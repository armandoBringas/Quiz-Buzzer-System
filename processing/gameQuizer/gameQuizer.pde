import processing.video.*;

PFont font; // Setup font
Timer setTimer; // Setup timer
Boolean showGameStart = true; // Show game start cover
Boolean startGame = false; // To start with game
int numberOfPlayers = 2; // Default number of players
int minNumberOfPlayers = 2; // Minimum number of players
int maxNumberOfPlayers = 3; // Maximum number of players
int lessons = 1; // The number of lessons

Lesson lesson1;

public void setup() {
  // Setup screen size
  size(1280, 720, JAVA2D);
  // Setup Text Preferences
  font = loadFont("Aharoni-Bold-250.vlw");
  textFont(font);
  textAlign(CENTER);
  // this.setTimer = new Timer(10);
}

public void draw() {
  background(68,114,202);
    
  // Show Game Cover
  if(this.showGameStart == true) {
    this.gameStart();  
  } else {
    // Show Select number of players frame
    if (this.startGame == false) {
      this.selectNumberOfPlayers(); 
    } else {
      // Start with lessons and questions
      lesson1 = new Lesson(this);
      lesson1.showLessonMovie();
    }
  }

  
  /*
  this.setTimer.countDown();
  text(setTimer.getTime(), 20, 20);
  */
  
}

// This method is to display game starting cover
public void gameStart() {
  textSize(100);
  fill(255);
  text("Bring Us Knowledge", width/2, height/2);
  textSize(50);
  fill(207, 222, 231);
  text("Press any key to start!...", width/2, height/2 + height/8);
  if (keyPressed == true) {
    this.showGameStart = false;
  }
}

// This method is to display and select the number of players for the game
public void selectNumberOfPlayers() {
  // Text to display
  selectNumberOfPlayersText();
  
  // Slow frameRate to control keyboard sensivity
  frameRate(10);
  
  // Setup number of players
  if (keyPressed == true) {
    // Check first if is coded
    if (key == CODED) {
      if (keyCode == UP) {
        if (this.numberOfPlayers < this.maxNumberOfPlayers) {
          this.numberOfPlayers += 1;
        }
      } else if (keyCode == DOWN) {
        if (this.numberOfPlayers > this.minNumberOfPlayers) {
          this.numberOfPlayers -= 1;
        }
      } 
    // If keyPressed is Enter continues with the game
    } else if (key == ENTER) {
      this.startGame = true;
    } 
  }
  
  // Return frameRate to default value
  frameRate(60);
}

public void selectNumberOfPlayersText(){
  textSize(75);
  fill(255);
  text("Select Number of Players", width/2, height/6);
  textSize(30);
  fill(146, 180, 244);
  text("Use the up or down arrow keys", width/2, height/4);
  textSize(250);
  fill(242, 255, 73);
  text(this.numberOfPlayers, width/2, height/2 + height/16);
  textSize(50);
  text("Press Enter to continue!", width/2, height - height/8);
}

  // Called every time a new frame is available to read
  void movieEvent(Movie m) {
    m.read();
    redraw = true;
  }
