import processing.core.PApplet;
import processing.core.PFont;

public class RunProcessing extends PApplet{

    public static void main(String... args){
        PApplet.main("RunProcessing");
    }

    PFont font;

    public void settings(){
        size(500, 500);
    }

    public void draw(){

        background(100,100,100);

        // font setup
        font = loadFont("AgencyFB-Bold-200.vlw");
        textFont(font);
        textAlign(CENTER);
        textSize(100);
        text("Trivia Game", width/2, 100);
        textSize(150);

        // timer setup
        int s = second();
        int m = minute();
        int h = hour();
        text(h+":"+m+":"+s, width/2, (height/2)+50);
    }
}