import java.util.HashMap;

public class Lesson {

    private int number;
    private String question;
    private HashMap<String, Boolean> answers;

    Lesson(int number, String question, HashMap<String, Boolean> answers) {
        this.number = number;
        this.question = question;
        this.answers = answers;
    }

    public int getNumber() {
        return this.number;
    }

    public String getQuestion() {
        return this.question;
    }

    public HashMap<String, Boolean> getAnswers(){
        return this.answers;
    }

    public Boolean isCorrectAnswer(String answer){
        if (this.answers.containsKey(answer)){
            return this.answers.get(answer).booleanValue();
        } else {
            return false;
        }
    }
}
