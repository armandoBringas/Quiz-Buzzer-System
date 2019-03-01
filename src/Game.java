import processing.core.PApplet;
import processing.core.PFont;

public class Game extends PApplet{

    // Fonts setup
    private PFont font;

    // Frames to show
    private boolean showGameCover = true;
    private boolean showGameSettings = false;
    private boolean showGameTopic = false;

    // Start Processing
    public static void main(String... args){
        PApplet.main("Game");
    }

    // Processing Settings
    public void settings(){
        // Screen settings
        this.size(1280, 720, JAVA2D);

        //Load fonts
        this.font = loadFont("AgencyFB-Bold-200.vlw");
    }

    // Processing Display
    public void draw(){
        // Set game background
        this.background(68,114,202);

        // Display game frames
        if (this.showGameCover){
            this.gameCover();
        } else if (this.showGameSettings) {
            this.gameSettings();
        } else if (this.showGameTopic)
            this.gameTopic();
    }

    // Frame : Game Cover
    private void gameCover(){

        // Game title
        this.textFont(this.font);
        this.textAlign(CENTER);
        this.textSize(100);
        this.fill(255);
        this.text("Bring Us Knowledge", width / 2, height /2);

        //  "Press any button" title
        this.textSize(50);
        this.fill(207, 222, 231);
        this.text("Press any key to start!...", width/2, height/2 + height/8);

        // Detect key pressed to change to next frame
        if (this.keyPressed && this.showGameCover && !this.showGameSettings){
            this.showGameCover = false;
            this.showGameSettings = true;
        }

    }

    /*
     * Frame : Game Settings
     * Select number of players
     * Select topic to play
     */
    private void gameSettings(){

    }

    /*
     * Frame : Game Topic
     * Display lesson Video
     * Display lesson questions
     */
    private void gameTopic(){

    }
}
