import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

public class Game extends PApplet{

    private PFont font;
    private String gameState;
    private String gamePlayState;
    private int buttonDelay = 250;
    private Timer timer;
    public static Player[] players;

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
        this.fill(207, 222, 231);
        this.text("Select your game settings", width/2, height/6);

        // Show text 2 & 3
        this.textSize(50);
        this.fill(255);
        this.text("Select number of players:", width/2, height/2 - height/32);
        this.text("Select topic to play:", width/2, height/2 + height/16);


        // Set number of players
        // TODO control according with player selection
        int n = 2;
        players = new Player[n];
        for (int i = 0; i < n; i++){
            players[i] = new Player(i);
        }

        // Continue button
        Button button = new Button(this, false, 350, 125, this.width/2, this.height - 125, "Start Game!");
        if(this.mousePressed && button.overCircle()){
            this.gameState = "TOPIC";
            this.delay(buttonDelay);
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
            this.delay(buttonDelay);
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
        this.textSize(85);
        this.fill(255);
        this.text("Lesson A", this.width / 2, 85 + this.height/32);

        // Display images and text
        this.imageMode(CENTER);
        image(this.gameLesson, this.width/4, this.height/2, width/3, width/3);
        image(this.gamePlay, (3*width)/4, this.height/2, width/3, width/3);

        Button button = new Button(this, true, 50, 50, this.width/2, this.height/2 + this.height/16, "Start Game!");
        if(this.mousePressed && button.overCircle()){
            this.gamePlayState = "LESSON-PLAY";
            this.delay(buttonDelay);
        }
    }

    private void playLesson(){
        this.timer.countDown();

        this.text((int)this.timer.getTime(), this.width / 2, this.height /2);
    }

}
