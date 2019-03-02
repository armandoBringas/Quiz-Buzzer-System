import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONObject;

// import java.io.IOException;

public class Game extends PApplet{

    private PFont font;
    private String gameState;
    private String gamePlayState;
    private int buttonDelay = 250;
    private Timer timer;

    //Players
    private  int nPlayers = 2;
    public static Player[] players;

    //JSON Objects of topics
    private JSONObject[] topicJSONs;

    //Images
    private PImage gamePlay;
    private PImage gameLesson;

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

        //Load json
        this.topicJSONs = new JSONObject[2];
        this.topicJSONs[0] = this.loadJSONObject("topic1.json");
        this.topicJSONs[1] = this.loadJSONObject("topic2.json");
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
                players[i] = new Player(i);
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
        int nLessons = 4;
        for (int i = 0; i < nLessons; i ++){

            switch (this.gamePlayState) {
                case "LESSON-COVER":
                    this.coverLesson();
                    break;
                case "LESSON-PLAY":

                    this.playLesson();
                    break;
                case "LESSON-RESULTS":
                    break;
            }
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
        this.timer.countDown();

        this.text((int)this.timer.getTime(), this.width / 2, this.height /2);
    }
}
