import processing.core.PApplet;
import processing.core.PFont;

public class Game extends PApplet{

    // Fonts setup
    private PFont font;

    // Frames to show
    private boolean showGameCover = true;
    private boolean showGameSettings = false;
    private boolean showGameTopic = false;

    public static Player[] players;

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
        this.background(68,114,202);

        // Slow frame rate for options selection
        this.frameRate(10);

        // Display game frames
        if (this.showGameCover){
            this.gameCover();
        } else {
            if (this.showGameSettings) {
                this.gameSettings();
            } else {
                if (this.showGameTopic) {
                    this.gameTopic();
                }
            }
        }
    }

    // Frame : Game Cover
    private void gameCover(){
        // Show "Bring Us Knowledge" title
        this.textFont(this.font);
        this.textAlign(CENTER);
        this.textSize(100);
        this.fill(255);
        this.text("Bring Us Knowledge", width / 2, height /2);

        // Show "Press any button" title
        this.textSize(50);
        this.fill(207, 222, 231);
        this.text("Press any key to start!...", width/2, height/2 + height/8);

        // Detect key pressed to change to next frame
        if (this.keyPressed && this.showGameCover && !this.showGameSettings && !this.showGameTopic){
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

        // Detect key pressed to change to next frame
        if (this.keyPressed && !this.showGameCover && this.showGameSettings && !this.showGameTopic){
            this.showGameSettings = false;
            this.showGameTopic = true;
        }
    }

    /*
     * Frame : Game Topic
     * Display lesson Video
     * Display lesson questions
     */
    private void gameTopic(){
        Topic topic = new Topic(this, "Grammar");
        topic.startTopic();
    }
}
