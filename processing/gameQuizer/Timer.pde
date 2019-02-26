class Timer {
  float Time;
  
  // Constructor of the Timer class
  Timer(float set) {
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
      this.Time -= 1/frameRate; 
    } else {
      this.Time = 0;
    }
  }
  
  
}
