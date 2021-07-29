package online.rkmhikai.model;

public class Quiz {
    int quizID;
    int lectureID;
    String title;
    String description;

    public Quiz(){

    }

    public Quiz(int quizID, int lectureID, String title, String description) {
        this.quizID = quizID;
        this.lectureID = lectureID;
        this.title = title;
        this.description = description;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getLectureID() {
        return lectureID;
    }

    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
