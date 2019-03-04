import processing.data.JSONObject;
import processing.data.JSONArray;
import java.util.*;

public class Topic {

    private int ID;
    private String description;
    private List<Lesson> lessonsList;
    private String video;

    public Topic(int ID, String description, String video){
        this.ID = ID;
        this.description = description;
        this.video = video;
        this.lessonsList = new ArrayList<>();
    }

    public Topic(JSONObject topic){
        Object[] allKeys = topic.keys().toArray();
        JSONArray objTopic = topic.getJSONArray( (String) allKeys[0] );//"Topic 1");//.getJSONObject("Topic 1");
        JSONObject objTopicProperty;
        JSONObject objItem;
        JSONObject itemProp;
        JSONArray arrItem;
        Lesson lesson;
        HashMap<String, Boolean> answers = new HashMap<String, Boolean>();
        this.lessonsList = new ArrayList<>();

        try {

            objTopicProperty = objTopic.getJSONObject(0);
            this.description = objTopicProperty.get("description").toString();
            this.video = objTopicProperty.get("video").toString();

//            System.out.println(this.description);
//            System.out.println(this.video);

            for ( int i = 1 ; i < objTopic.size() ; i++ ){
                objItem = objTopic.getJSONObject(i);

                answers.putAll(extractAnswer(objItem, "option1"));
                answers.putAll(extractAnswer(objItem, "option2"));
                answers.putAll(extractAnswer(objItem, "option3"));
                answers.putAll(extractAnswer(objItem, "option4"));
//                arrItem = objItem.getJSONArray("option1");
//                itemProp = arrItem.getJSONObject(0);
//                answers.put(itemProp.getString("answer"), itemProp.getBoolean("isCorrect"));
                lesson = new Lesson(objItem.getInt("ID"), objItem.getString("question"), answers);
                this.lessonsList.add(lesson);
            }

        } catch (NullPointerException npe) {

        }
    }

    private HashMap<String, Boolean> extractAnswer(JSONObject objItem, String keyName){
        JSONArray arrItem;
        JSONObject itemProp;
        HashMap<String, Boolean> currentAnswer = new HashMap<String, Boolean>();

        arrItem = objItem.getJSONArray(keyName);
        itemProp = arrItem.getJSONObject(0);
        currentAnswer.put(itemProp.getString("answer"), itemProp.getBoolean("isCorrect"));

        return  currentAnswer;
    }

    public int getID() {
        return this.ID;
    }

    public String getDescription() {
        return this.description;
    }

    public String getVideo(){
        return this.video;
    }

    public List<Lesson> getLessonsList() {
        return this.lessonsList;
    }

    //Add lessons to the list
    public void addLesson(Lesson lesson){
        this.lessonsList.add(lesson);
    }
}
