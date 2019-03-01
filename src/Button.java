import processing.core.PApplet;
import processing.core.PConstants;

public class Button {

    private PApplet p;
    private float diameter;
    private float locX;
    private float locY;

    Button(PApplet parent, float diameter, float locX, float locY) {
        this.p = parent;
        this.diameter = diameter;
        this.locX = locX;
        this.locY = locY;

        this.p.ellipseMode(PConstants.CENTER);
        this.p.fill(255);
        this.p.ellipse(locX, locY, diameter, diameter);
    }

    boolean overCircle(){
        float disX = this.locX - this.p.mouseX;
        float disY = this.locY - this.p.mouseY;

        return (PApplet.sqrt(PApplet.sq(disX) + PApplet.sq(disY)) < this.diameter / 2);
    }
}
