import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONObject;
import java.util.HashMap;

// import java.io.IOException;

public class Game extends PApplet{

    private PFont font;
    private String gameState;
    private String gamePlayState;
    private int buttonDelay = 250;

    //Timer
    private Timer timer;
    private Boolean stopTimer = false;
    private int playerToRespondID;

    //Players
    private  int nPlayers = 2;
    private static Player[] players;

    //Topic with lessons
    private HashMap<String, Lesson[]> topic = new HashMap<String, Lesson[]>();

    //JSON Objects of topics
    private JSONObject[] topicJSONs;

    //Images
    private PImage gamePlay;
    private PImage gameLesson;
    private PImage imgCorrectAnswer;
    private PImage imgIncorrectAnswer;

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

        //TODO populate topics hash table with .json files
        this.topicJSONs = new JSONObject[2];
        this.topicJSONs[0] = this.loadJSONObject("topic1.json");
        this.topicJSONs[1] = this.loadJSONObject("topic2.json");

        //TODO Remove Hard coded filled of topics - Build Topic Class
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

        this.fill(207, 222, 231);
        this.text("Select number of players: " + this.nPlayers, width/2, height/2 - height/8);
        this.text("Select topic to play:", width/2, height/2 + height/16);


        // Continue button
        Button button = new Button(this, false, 350, 125, this.width/2, this.height - 125, "Start Game!", true);
        if(this.mousePressed && button.overRectangle()){
            this.gameState = "TOPIC";
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
                    this.playResults();
                    break;
            }
    }

    private void coverLesson(){
        // Show text 1
        this.textAlign(PConstants.CENTER);
        this.textSize(85); //85
        this.fill(255);
        this.text("Lesson 1", this.width / 2, 85 + this.height/32);

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
            PApplet.launch(this.sketchPath("")+"src\\SampleVideo.mp4");
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
        //Show Question
        this.fill(255);
        this.textSize(100);
        this.text("What is X?", this.width / 2, 100 + this.height/32);

        //Show Answers
        this.textAlign(LEFT, CENTER);
        int textSize = 50;
        this.textSize(textSize);
        this.text("A) Option 1", 100, this.height/2 + this.height/16 - (textSize*2 + 12));
        this.text("B) Option 2", 100, this.height/2 + this.height/16 - (textSize/2 + 12));
        this.text("C) Option 3", 100, this.height/2 + this.height/16 + (textSize/2 + 12 ));
        this.text("D) Option 4", 100, this.height/2 + this.height/16 + (textSize*2 + 12));


        this.textAlign(CENTER, CENTER);

        //Show timer
        if (!this.stopTimer){
            this.timer.countDown();
        }
        this.stroke(255);
        this.strokeWeight(3);
        this.fill(0,0,255);
        this.ellipse((3*this.width)/4, this.height /2 + this.height/16, 300, 300);
        this.textSize(200);
        this.fill(255,140,0);
        this.text((int)this.timer.getTime(), (3*this.width)/4, this.height /2 + this.height/16);

        // Player press button
        if (this.keyPressed){
            if(this.key == '1'){
                this.stopTimer = true;
                this.delay(this.buttonDelay);
                // ID: 1 is player 1
                this.playerToRespondID = 1;
            } else if(this.key == '2'){
                this.stopTimer = true;
                this.delay(this.buttonDelay);
                // ID: 2 is player 1
                this.playerToRespondID = 2;
            }
        }

        // Evaluate Response
        if(this.keyPressed && this.stopTimer){
            switch (String.valueOf(key).toUpperCase()){
                case "A":
                    this.gamePlayState = "LESSON-RESULTS";

                case "B":
                    this.gamePlayState = "LESSON-RESULTS";

                case "C":
                    this.gamePlayState = "LESSON-RESULTS";

                case "D":
                    this.gamePlayState = "LESSON-RESULTS";
            }
            this.delay(this.buttonDelay);
        }


        // Time out
        if (this.timer.getTime() == 0){
            this.gamePlayState = "LESSON-RESULTS";
            this.timer.setTime(10);
            this.stopTimer = false;
        }
    }

    private void playResults(){
        //Show text 1
        this.fill(255);
        this.text("It's a draw!", this.width / 2, 85 + this.height/32);

        // Show text 2
        this.textSize(50);
        this.fill(207, 222, 231);
        this.text("Click anywhere to continue!...", width/2, height - height/8);

        //Show image
        this.image(this.imgIncorrectAnswer, this.width/2, this.height/2, this.width/4, this.width/4);

        if(this.mousePressed){
            this.gamePlayState = "LESSON-PLAY";
            this.delay(this.buttonDelay);
        }
    }

    private void hardCodedTopic1(){

    }
}
