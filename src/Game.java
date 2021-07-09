import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.serial.*;
import java.util.HashMap;
import java.util.ArrayList;

// import java.io.IOException;

public class Game extends PApplet{

    private PFont font;
    private String gameState;
    private String gamePlayState;
    private int buttonDelay = 250;
    private float fadeFloat;
    private boolean fadeOut = false;

    //Timer
    private Timer timer;
    private Boolean stopTimer = false;
    private int playerToRespondID;

    //Players
    private int nPlayers = 2;
    private static Player[] players;
    private boolean isValidResponse;
    private int winnerPlayer;

    //Topic with lessons
    private HashMap<String, Lesson[]> topic = new HashMap<String, Lesson[]>();

    //JSON Objects of topics
    private JSONObject[] topicJSONs;

    private ArrayList<Topic> jsonTopics = new ArrayList<Topic>();
    private int currentTopic;
    private int currentQuestion;

    //Images
    private PImage gamePlay;
    private PImage gameLesson;
    private PImage imgCorrectAnswer;
    private PImage imgIncorrectAnswer;

    //Arduino
    Serial myPort;  // Create object from Serial class
    String val;     // Data received from the serial port

    // Start Processing
    public static void main(String... args){
        PApplet.main("Game");
    }

    // Processing Settings
    public void settings(){
        this.size(1280, 720, JAVA2D);
        this.font = loadFont("AgencyFB-Bold-200.vlw");
        this.gameState = "START";
        this.timer = new Timer(this, 10);

        //Load images
        this.gamePlay = loadImage("PlayGameLesson.png");
        this.gameLesson = loadImage("PlayVideoLesson.png");
        this.imgCorrectAnswer = loadImage("correctAnswer.png");
        this.imgIncorrectAnswer = loadImage("incorrectAnswer.png");

        //Current FilePath
        java.io.File parentDir = new java.io.File(this.sketchPath()+"\\resources");
        String[] dirs = parentDir.list();
        for (int i = 0 ; i < dirs.length ; i++) {
            if (dirs[i].toLowerCase().matches("^topic.*.json$")) {
                JSONObject obj = loadJSONObject(this.sketchPath()+ "\\resources\\" + dirs[i]);
                Topic t = new Topic(obj);
                t.setVideo(this.sketchPath() + "\\resources\\" + t.getVideo());
                jsonTopics.add(t);
            }
        }
        if (jsonTopics.size() > 0) {
            this.currentTopic = 0;
        }
        this.currentQuestion = 0;

        //TODO populate topics hash table with .json files
        this.topicJSONs = new JSONObject[2];
        this.topicJSONs[0] = this.loadJSONObject("topic1.json");
        this.topicJSONs[1] = this.loadJSONObject("topic2.json");

        //Read arduino
        String portName = Serial.list()[2]; //change the 0 to a 1 or 2 etc. to match your port
        myPort = new Serial(this, portName, 9600);
    }

    // Processing Display
    public void draw(){
        this.cursor(PConstants.CROSS);
        this.background(68,114,202);

        switch (this.gameState) {
            case "START":
                this.startGame();
                break;
            case "SETTINGS":
                this.settingsGame();
                break;
            case "TOPIC":
                this.topicGame();
                break;
            case "PLAY":
                this.playGame();
            case "WIN":
                // Show winner
                break;
        }
    }

    // Frame : Game Cover
    private void startGame(){
        // Show text 1
        this.textFont(this.font);
        this.textAlign(CENTER);
        this.textSize(100);
        this.fill(255);
        this.text("Bring Us Knowledge", width / 2, height /2);

        // Show text 2
        this.textSize(50);
        this.fill(207, 222, 231);
        this.text("Click anywhere to start!...", width/2, height/2 + height/8);

        if(this.mousePressed){
            this.gameState = "SETTINGS";
            this.delay(buttonDelay);
        }
    }

    /*
     * Frame : Game Settings
     * Select number of players
     * Select topic to play
     */
    private void settingsGame(){
        // Show text 1
        this.textSize(100);
        this.fill(255);
        this.text("Select your game settings", width/2, height/6);

        // Show text 2 & 3
        this.textSize(50);

        Button btnPlayers = new Button(this, false, 500, 60, this.width/2, this.height/2 - this.height/8, "", false);
        if(this.mousePressed && btnPlayers.overRectangle()){
            this.nPlayers++;
            this.delay(this.buttonDelay);

            // Reset players to 1
            if(this.nPlayers > 3){
                this.nPlayers = 1;
            }
        }

        this.stroke(0);

        Button btnTopics = new Button(this, false, 500, 60,
                this.width/2, this.height/2 + height/16,
                "", false);
        if(this.mousePressed && btnTopics.overRectangle()){
            if (currentTopic < jsonTopics.size() - 1 ) {
                currentTopic += 1;
            } else {
                currentTopic = 0;
            }
            this.delay(this.buttonDelay);
        }

        this.fill(207, 222, 231);
        this.text("Select number of players: " + this.nPlayers, width/2, height/2 - height/8);
        this.text("Select topic to play: " + jsonTopics.get(currentTopic).getDescription(),
                    width/2, height/2 + height/16);


        // Continue button
        Button button = new Button(this, false, 350, 125, this.width/2, this.height - 125, "Start Game!", true);
        if(this.mousePressed && button.overRectangle()){
//            this.gameState = "TOPIC";
            this.gameState = "PLAY";
            this.gamePlayState = "LESSON-COVER";
            this.delay(this.buttonDelay);

            // Setup players
            players = new Player[this.nPlayers];
            for (int i = 0; i < this.nPlayers; i++){
                players[i] = new Player(i + 1);
            }

        }
    }

    /*
     * Frame : Game Topic
     * Display topic to play
     */
    private void topicGame(){
        // Show text 1
        this.textFont(this.font);
        this.textAlign(CENTER);
        this.textSize(100);
        this.fill(255);
        this.text("Topic X", width / 2, height /2);

        // Show text 2
        this.textSize(50);
        this.fill(207, 222, 231);
        this.text("Click anywhere to start!...", width/2, height/2 + height/8);

        if(this.mousePressed){
            this.gameState = "PLAY";
            this.gamePlayState = "LESSON-COVER";
            this.delay(this.buttonDelay);
        }
    }

    private void playGame(){
            switch (this.gamePlayState) {
                case "LESSON-COVER":
                    this.coverLesson();
                    break;
                case "LESSON-PLAY":
                    this.playLesson();
                    break;
                case "LESSON-RESULTS":
                    this.playResults(this.isValidResponse);
                    break;
                case "LESSON-FINAL":
                    this.showFinalResults();
                    break;
            }
    }

    private void coverLesson(){
        // Show text 1
        this.textAlign(PConstants.CENTER);
        this.textSize(85); //85
        this.fill(255);
//        this.text("Lesson 1", this.width / 2, 85 + this.height/32);
        this.text(jsonTopics.get(currentTopic).getDescription(), this.width / 2, 85 + this.height/32);

        // Display images and text
        this.imageMode(CENTER);
        this.textSize(50);
        this.fill(252, 226, 5);
        this.text("Play Lesson", this.width/4, 50 + (this.height/2) +((this.width/3)/2));
        image(this.gameLesson, this.width/4, this.height/2, this.width/3, this.width/3);
        this.text("Play Game!", (3*this.width)/4, 50 + (this.height/2) +((this.width/3)/2));
        image(this.gamePlay, (3*this.width)/4, this.height/2, this.width/3, this.width/3);

        //Buttons for images
        Button buttonLesson = new Button(this, true, this.width/3, this.width/3, this.width/4, this.height/2, "", false);
        if(this.mousePressed && buttonLesson.overCircle()){
            PApplet.launch(jsonTopics.get(currentTopic).getVideo());
//            PApplet.launch(this.sketchPath("")+"src\\SampleVideo.mp4");
            /*
            try {
                // Runtime.getRuntime().exec("C:\\Program Files\\Windows Media Player\\wmplayer.exe /play C:\\Users\\Armando\\Documents\\GitHub\\Quiz-Buzzer-System\\src\\SampleVideo.mp4");
            } catch (IOException e){
                e.printStackTrace();
            }
            */
        }

        Button buttonPlay = new Button(this, true, this.width/3, this.width/3, (3*this.width)/4, this.height/2, "", false);
        if(this.mousePressed && buttonPlay.overCircle()){
            this.gamePlayState = "LESSON-PLAY";
            this.delay(this.buttonDelay);
        }
    }

    private void playLesson(){
        java.util.List<Lesson> lessons = lessons = jsonTopics.get(currentTopic).getLessonsList();
        Lesson lesson = lessons.get(currentQuestion);
        int i = 0;
        //Show Question
        this.fill(255);
        this.textSize(75);
        if (lesson.getQuestion().length() > 40) {this.textSize(50);}
//        this.text("What is X?", this.width / 2, 100 + this.height/32);
        this.text(lesson.getQuestion(), this.width / 2, 100 + this.height/32);

        //Show Answers
        this.textAlign(LEFT, CENTER);
        int textSize = 50;
        this.textSize(textSize);
//        this.text("A) Option 1", 100, this.height/2 + this.height/16 - (textSize*2 + 12));
//        this.text("B) Option 2", 100, this.height/2 + this.height/16 - (textSize/2 + 12));
//        this.text("C) Option 3", 100, this.height/2 + this.height/16 + (textSize/2 + 12 ));
//        this.text("D) Option 4", 100, this.height/2 + this.height/16 + (textSize*2 + 12));
        lesson.getAnswers().keySet();
        java.util.Iterator<String> itr = lesson.getAnswers().keySet().iterator();
        while (itr.hasNext()) {
            int y = 0;
            String outStr = "";
            switch(i) {
                case 0:
                    y = this.height/2 + this.height/16 - (textSize*2 + 12);
                    outStr += "A) ";
                    break;
                case 1:
                    y = this.height/2 + this.height/16 - (textSize/2 + 12);
                    outStr += "B) ";
                    break;
                case 2:
                    y = this.height/2 + this.height/16 + (textSize/2 + 12);
                    outStr += "C) ";
                    break;
                case 3:
                    y = this.height/2 + this.height/16 + (textSize*2 + 12);
                    outStr += "D) ";
                    break;
            }
            outStr += itr.next();
            this.text(outStr, 50, y);
            i++;
        }


        this.textAlign(CENTER, CENTER);

        //Show timer
        if (!this.stopTimer){
            this.timer.countDown();
            this.fadeFloat = 0;
        } else {
            this.fill(255, fadeFloat);
            this.text("Waiting for Player " + this.playerToRespondID + " response", width/2, (height/6)*5 );
            if (this.fadeFloat <= 0 ){
                fadeOut = true;
            } else if (fadeFloat > 255){
                fadeOut = false;
            }
            if (fadeOut ) {
                this.fadeFloat += 3;
            } else {
                this.fadeFloat -= 3;
            }
        }

        //Timer
        this.stroke(255);
        this.strokeWeight(3);
        this.fill(0,0,255);
        this.ellipse((3*this.width)/4, this.height /2 + this.height/16, 300, 300);
        this.textSize(200);
        this.fill(255,140,0);
        this.text((int)this.timer.getTime(), (3*this.width)/4, this.height /2 + this.height/16);

        // Player press button
        // Read Arduino port
        /*if ( val == null && myPort.available() > 0)
        {  // If data is available,
            val = myPort.readStringUntil('\n');         // read it and store it in val
        }*/

        if (val == null){
            if(myPort.available() > 0) {
                val = myPort.readStringUntil('\n');     // read it and store it in val
                //myPort.clear();
                //myPort.stop();
            }
        }
        //System.out.println(val); //print it out in the console

        if ( val != null ){
            if(val.charAt(0) == '1') { //equals("1")){
                this.stopTimer = true;
                //this.delay(this.buttonDelay);
                // ID: 1 is player 1
                this.playerToRespondID = 1;
            } else if(val.charAt(0) == '2' ) { //.equals("2")){
                this.stopTimer = true;
                //this.delay(this.buttonDelay);
                // ID: 2 is player 1
                this.playerToRespondID = 2;
            }
        }

        // Evaluate Response
        if(val != null && keyPressed && this.stopTimer){

            Object[] allKeys = lesson.getAnswers().keySet().toArray();
            boolean isCorrect = false;
            boolean validKey = false;
            int answerKey = -1;
            switch (String.valueOf(key).toLowerCase()) {
                case "a":
                    answerKey = 0;
//                    if (0 < allKeys.length ) {
//                        isCorrect = lesson.isCorrectAnswer(allKeys[0].toString());
//                    }
                    validKey = true;
                    break;
                case "b":
                    answerKey = 1;
//                    if (1 < allKeys.length ) {
//                        isCorrect = lesson.isCorrectAnswer(allKeys[1].toString());
//                    }
                    validKey = true;
                    break;
                case "c":
                    answerKey = 2;
//                    if (2 < allKeys.length ) {
//                        isCorrect = lesson.isCorrectAnswer(allKeys[2].toString());
//                    }
                    validKey = true;
                    break;
                case "d":
                    answerKey = 3;
//                    if (3 < allKeys.length ) {
//                        isCorrect = lesson.isCorrectAnswer(allKeys[3].toString());
//                    }
                    validKey = true;
                    break;
                default: validKey = false;
                break;
            }

            if (validKey) {
                if ( answerKey >= 0 && answerKey < allKeys.length ) {
                    isCorrect = lesson.isCorrectAnswer(allKeys[answerKey].toString());
                }
                if (isCorrect) {
                    players[this.playerToRespondID - 1].addScore();
                    this.isValidResponse = true;
                    this.winnerPlayer = this.playerToRespondID;
                    this.gamePlayState = "LESSON-RESULTS";
                    this.timer.setTime(10);
                    this.stopTimer = false;
                } else {
                    this.isValidResponse = false;
                    this.gamePlayState = "LESSON-RESULTS";
                    this.timer.setTime(10);
                    this.stopTimer = false;
                }
            }
//            switch (String.valueOf(key).toUpperCase()){
//                // false
//                case "A":
//                    this.gamePlayState = "LESSON-RESULTS";
//                    this.isValidResponse = false;
//                    this.timer.setTime(10);
//                    this.stopTimer = false;
//                    break;
//                // true
//                case "B":
//                    this.gamePlayState = "LESSON-RESULTS";
//                    players[this.playerToRespondID - 1].addScore();
//                    this.isValidResponse = true;
//                    this.winnerPlayer = this.playerToRespondID;
//                    this.timer.setTime(10);
//                    this.stopTimer = false;
//                    break;
//                // false
//                case "C":
//                    this.gamePlayState = "LESSON-RESULTS";
//                    this.isValidResponse = false;
//                    this.timer.setTime(10);
//                    this.stopTimer = false;
//                    break;
//                // false
//                case "D":
//                    this.gamePlayState = "LESSON-RESULTS";
//                    this.isValidResponse = false;
//                    this.timer.setTime(10);
//                    this.stopTimer = false;
//                    break;
//            }
//            this.delay(this.buttonDelay);
        }


        // Time out
        if (this.timer.getTime() == 0 && !this.isValidResponse){
            this.gamePlayState = "LESSON-RESULTS";
            this.isValidResponse = false;
            this.timer.setTime(10);
            this.stopTimer = false;

        }
    }

    private void playResults(Boolean isValidResponse){
        //reset Arduino response
        val = null;

        //show answers
        this.textAlign(CENTER);
        this.textSize(50);
        this.fill(255);
        this.text("Correct answer:", this.width/5 , (this.height/8)*4);
        Lesson lesson = jsonTopics.get(currentTopic).getLessonsList().get(currentQuestion);
        java.util.Set<String> keys = lesson.getAnswers().keySet();
        java.util.Iterator<String> itr = lesson.getAnswers().keySet().iterator();
        while (itr.hasNext()) {
            String ans = itr.next();
            boolean isCorrect = lesson.getAnswers().get(ans);
            if (ans.length() > 20) {
                this.textSize(25);
            }
            if (isCorrect) {
              this.text(ans, width/5, (height/8)*5);
            }
        }
//        this.text("Player 1: " + String.valueOf(players[0].getScore()), (3*this.width)/4, this.height/2 - this.height/16 + 12);
//        this.text("Player 2: " + String.valueOf(players[1].getScore()), (3*this.width)/4, this.height/2 + this.height/16 + 12);

        // Show scores
        this.textAlign(LEFT, CENTER);
        this.textSize(50);
        this.fill(252, 226, 5);
        this.text("Scores:", this.width/2 , this.height/2);
        this.text("Player 1: " + String.valueOf(players[0].getScore()), (3*this.width)/4, this.height/2 - this.height/16 + 12);
        this.text("Player 2: " + String.valueOf(players[1].getScore()), (3*this.width)/4, this.height/2 + this.height/16 + 12);

        // Validate response
        this.textAlign(CENTER);
        this.textSize(75);
        this.fill(255);
        if (isValidResponse){
            this.image(this.imgCorrectAnswer, this.width/2, this.height/2, this.width/4, this.width/4);
            this.text("Player " + this.winnerPlayer + " wins!", this.width / 2, 100 + this.height/32);
        } else {
            //Show text 1
            this.text("It's a draw!", this.width / 2, 100 + this.height/32);

            //Show image
            this.image(this.imgIncorrectAnswer, this.width/2, this.height/2, this.width/4, this.width/4);
        }

        // Show text 2
        this.textAlign(CENTER);
        this.textSize(50);
        this.fill(207, 222, 231);
        this.text("Click anywhere to continue!...", width/2, height - height/8);

        if(this.mousePressed){
            this.gamePlayState = "LESSON-PLAY";
            if (this.currentQuestion < this.jsonTopics.get(currentTopic).getLessonsList().size() - 1) {
                this.currentQuestion++;
            } else {
                this.gamePlayState = "LESSON-FINAL";
            }
            this.delay(this.buttonDelay);
        }
    }

    private void showFinalResults() {
        // Show scores
        this.textSize(50);
        this.fill(207, 222, 231);
        this.text("Final scores:", this.width/2 , this.height/2);
        this.text("Player 1: " + String.valueOf(players[0].getScore()), this.width/2, this.height/2 + this.height/16 + (50/2 + 12));
        this.text("Player 2: " + String.valueOf(players[1].getScore()), this.width/2, this.height/2 + this.height/16 + (50*2 + 12));

        //Show winner
        String winnerState = "Its's a draw!";
        if (players[0].getScore() > players[1].getScore()){
            winnerState = "Player 1 wins!";
        } else if (players[0].getScore() < players[1].getScore()){
            winnerState = "Player 2 wins1";
        }

        this.textSize(150);
        this.fill(252, 226, 5);
        this.text(winnerState, this.width/2, 150);

        this.currentQuestion = 0;

        if (this.mousePressed) {
            this.gameState = "SETTINGS";
            this.delay(100);
        }
    }

    private boolean validAnswerKey(Object[] allKeys, int answerInt) {
        if (answerInt > -1 && answerInt > allKeys.length) {
            return true;
        } else {
            return false;
        }
    }
}
