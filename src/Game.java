import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Game extends PApplet{

    private PFont font;
    private String gameState;
    private String gamePlayState;
    private int buttonDelay = 250;
    private Timer timer;
    public static Player[] players;

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
    }

    // Processing Display
    public void draw(){
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
        // Show "Select game settings title"
        this.textSize(75);
        this.fill(207, 222, 231);
        this.text("Select Game Settings", width/2, height/6);

        // Set number of players
        int n = 2;
        players = new Player[n];
        for (int i = 0; i < n; i++){
            players[i] = new Player(i);
        }

        Button button = new Button(this, 100, width/2, height/2);
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

        Button button = new Button(this, 100, width/2, height/2 + height/8);
        if(this.mousePressed && button.overCircle()){
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
        this.textSize(100);
        this.fill(255);
        this.text("Lesson A", this.width / 2, this.height /2);

        Button button = new Button(this, 50, this.width/2, this.height/2 + this.height/16);
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
