public class Player {

    private int score ;
    private int ID;

    // Constructor of the Topic class
    public Player(int playerID) {
        this.ID = playerID;
        this.score = 0;
    }

    // Get player score
    public int getScore(){
        return this.score;
    }

    // Method to add score to the player
    public int addScore(){
        return this.score += 1;
    }

    // Get player number
    public int getID(){
        return this.ID;
    }
}