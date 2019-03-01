import processing.core.PApplet;
import processing.core.PConstants;

public class Lesson {

    private PApplet p;
    private String name;
    private String lessonState;

    Lesson(PApplet parent, String lessonName) {
        this.p = parent;
        this.name = lessonName;
        this.lessonState = "START";
    }

    public void displayLesson(){
        this.p.background(68,114,202);

        switch (this.lessonState) {
            case "START":
                this.startLesson();
                break;
            case "PLAY":
                this.playGame();
                break;
        }
    }

    private void startLesson(){
        // Show text 1
        this.p.textAlign(PConstants.CENTER);
        this.p.textSize(100);
        this.p.fill(255);
        this.p.text("Lesson " + this.name, this.p.width / 2, this.p.height /2);

        Button button = new Button(this.p, 100, this.p.width/2, this.p.height/2 + this.p.height/16);
        if(this.p.mousePressed && button.overCircle()){
            this.lessonState = "PLAY";
        }

        this.displayLesson();
    }

    private void playGame(){
        this.p.text("test" + this.name, this.p.width / 2, this.p.height /2);
    }
}
