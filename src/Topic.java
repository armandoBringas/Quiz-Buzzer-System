import java.util.*;

public class Topic {

    private int ID;
    private String description;
    private List<Lesson> lessonsList;

    public Topic(int ID, String description){
        this.ID = ID;
        this.description = description;
        this.lessonsList = new ArrayList<>();
    }

    public int getID() {
        return this.ID;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Lesson> getLessonsList() {
        return this.lessonsList;
    }

    //Add lessons to the list
    public void addLesson(Lesson lesson){
        this.lessonsList.add(lesson);
    }
}
