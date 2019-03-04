import processing.core.PApplet;
import processing.core.PConstants;

public class Button {

    private PApplet p;
    private float sizeX;
    private float sizeY;
    private float locX;
    private float locY;

    Button(
            PApplet parent,
            boolean isCircular,
            float sizeX,
            float sizeY,
            float locX,
            float locY,
            String text,
            boolean isFilled) {
        this.p = parent;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.locX = locX;
        this.locY = locY;

        this.p.noStroke();

        if (isFilled){
            this.p.fill(169,169,169);
        } else {
            this.p.noFill();
        }

        if(isCircular){
            this.p.ellipseMode(PConstants.CENTER);
            this.p.ellipse(this.locX, this.locY, this.sizeX, this.sizeY);
        } else {
            this.p.rectMode(PConstants.CENTER);
            this.p.rect(this.locX, this.locY, this.sizeX, this.sizeY, this.sizeY/4, this.sizeY/4, this.sizeY/4, this.sizeY/4);
        }

        this.p.fill(32,32,32);
        this.p.textAlign(PConstants.CENTER, PConstants.CENTER);
        this.p.text(text, this.locX, this.locY);
    }

    boolean overCircle(){
        float disX = this.locX - this.p.mouseX;
        float disY = this.locY - this.p.mouseY;
        return (PApplet.sqrt(PApplet.sq(disX) + PApplet.sq(disY)) < this.sizeX / 2);
    }

    boolean overRectangle(){
        return this.p.mouseX >= this.locX
                && this.p.mouseX <= this.locX + this.sizeX
                && this.p.mouseY >= this.locY
                && this.p.mouseY <= this.locY + this.sizeY;
    }
}
