class Lesson {
  Movie lessonMovie;
  
  // Constructor 
  Lesson(PApplet parent) {
    
    if (this.lessonMovie == null) {
      (this.lessonMovie = new Movie(parent, "Bowler2_FV_250fps.avi")).play();
      }
      
    while (this.lessonMovie.width == 0) {
      delay(10);
    }
    
    frameRate(40);
      
  }
  
  void showLessonMovie() {
    imageMode(CENTER);
    image(this.lessonMovie, width/2, height/2);
  }
}
