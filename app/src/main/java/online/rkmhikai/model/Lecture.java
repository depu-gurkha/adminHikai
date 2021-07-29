package online.rkmhikai.model;

public class Lecture {
    private int lectureID;
    private String title;
    private  String desc;
    private String file;
    private int status;
    private String createdAt;

    public Lecture(int lectureID, String title, String desc, String file, int status,String createdAt) {
        this.lectureID = lectureID;
        this.title = title;
        this.desc = desc;
        this.file = file;
        this.status = status;
        this.createdAt=createdAt;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
