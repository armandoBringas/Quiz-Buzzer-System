import processing.core.PApplet;

class Topic {

    private PApplet parent;
    private String name;
    private boolean showCover = true;
    private boolean showLesson = false;

    // Constructor of the Topic class
    Topic(PApplet p, String topicName) {
        this.parent = p;
        this.name = topicName;
    }

    public void startTopic(){
        if (this.showCover){
            this.Cover();
        } else {
            if (this.showLesson){
                this.Lessons();
            }
        }
    }

    private void Cover(){
        parent.textSize(100);
        parent.fill(255);
        parent.text(this.name, parent.width / 2, parent.height /2);

        // Detect key pressed to change to next frame
        if (parent.keyPressed && this.showCover && !this.showLesson){
            this.showCover = false;
            this.showLesson = true;
        }
    }

    private void Lessons(){
        parent.textSize(100);
        parent.fill(255);
        parent.text("Lesson 1", parent.width / 2, parent.height /2);
    }
}