const int ledPlayer1 = 13;
const int ledPlayer2 = 12;
const int buttonPlayer1 = 3;
const int buttonPlayer2 = 2;
int buttonPlayer1State = 0;
int buttonPlayer2State = 0;


// Normally closed button constant
const int buttonPushed = LOW;
 

void setup()
{
   pinMode(ledPlayer1, OUTPUT);
   pinMode(ledPlayer2, OUTPUT);
   pinMode(buttonPlayer1, INPUT);
   pinMode(buttonPlayer2, INPUT);
   Serial.begin(9600);
}
 
void loop()
{
  buttonPlayer1State = digitalRead(buttonPlayer1);
  buttonPlayer2State = digitalRead(buttonPlayer2);

    if (buttonPlayer1State == buttonPushed){
      Serial.println("1");
      digitalWrite(ledPlayer1, HIGH);
      digitalWrite(ledPlayer2, LOW);
      delay(5000);
      
    } else if (buttonPlayer2State == buttonPushed){
      Serial.println("2");
      digitalWrite(ledPlayer1, LOW);
      digitalWrite(ledPlayer2, HIGH);
      delay(5000);
      
    } else {
      digitalWrite(ledPlayer1, LOW);
      digitalWrite(ledPlayer2, LOW);
      Serial.print("");
    }
}
