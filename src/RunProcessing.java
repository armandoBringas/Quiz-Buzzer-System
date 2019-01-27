import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.*;

public class RunProcessing extends PApplet{

    PFont font;
    Serial myPort;  // Create object from Serial class
    String val;     // Data received from the serial port

    public static void main(String... args){
        PApplet.main("RunProcessing");
    }


    public void settings(){
        size(500, 500);
        String portName = Serial.list()[2]; //change the 0 to a 1 or 2 etc. to match your port
        myPort = new Serial(this, portName, 9600);
    }

    public void draw(){

        background(100,100,100);

        // read arduino port
        if ( myPort.available() > 0)
        {  // If data is available,
            val = myPort.readStringUntil('\n');         // read it and store it in val
        }
        println(val); //print it out in the console

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