import processing.core.PApplet;

public class Timer {

    private float Time;
    private PApplet Parent;

    // Constructor of the Timer class
    public Timer(PApplet p, float set) {
        this.Parent = p;
        this.Time = set;
    }

    // Returns the current time
    float getTime() {
        return(this.Time);
    }

    // Set the timer to selected value
    void setTime(float set) {
        this.Time = set;
    }

    // Update the timer by counting down
    void countDown() {
        if (this.Time > 0) {
            this.Time -= 1/this.Parent.frameRate;
        } else {
            this.Time = 0;
        }
    }

}