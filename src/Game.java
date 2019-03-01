import processing.core.PApplet;
import processing.core.PFont;

public class Game extends PApplet{

    private PFont font;
    private String gameState;
    private String gamePlayState;
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
            case "PLAY":
                this.playGame();
                break;
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
            this.gameState = "PLAY";
            this.gamePlayState = "TOPIC";
        }
    }

    /*
     * Frame : Game Topic
     * Display lesson Video
     * Display lesson questions
     */
    private void playGame(){
        if (this.gamePlayState.equals("TOPIC")){
            this.topic();
        } else if(this.gamePlayState.equals("LESSONS")){

            Lesson lesson = new Lesson(this, "A");
            lesson.displayLesson();

            /*
            // Display Lessons
            int nLessons = 4;
            Lesson[] lessons = new Lesson[nLessons];
            for (int i = 0; i < nLessons; i ++){
                lessons[i] = new Lesson(this, "A");
            }
            */
        }
    }

    private void topic(){
        // Show text 1
        this.textFont(this.font);
        this.textAlign(CENTER);
        this.textSize(100);
        this.fill(255);
        this.text("Topic X", width / 2, height /2);

        Button button = new Button(this, 100, width/2, height/2 + height/8);
        if(this.mousePressed && button.overCircle()){
            this.gamePlayState = "LESSONS";
        }
    }


}
