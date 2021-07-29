package online.rkmhikai.model;

public class Subject {
    private int subId;
    private int status;
    private String title;
    private String desc;
    private String createdAt;
    private int courseID;
    private int chapterId;

    public Subject(int subId,String title, String desc,int status,String createdAt) {
        this.title = title;
        this.desc = desc;
        this.subId=subId;
        this.status=status;
        this.createdAt=createdAt;

    }

    public Subject() {
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public Subject(int subId, int chapterId , String title, String desc, int status,String createdAt) {
        this.subId = subId;
        this.title = title;
        this.desc = desc;
        this.status = status;
        this.chapterId=chapterId;
        this.createdAt=createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public int getStatus() {
        return status;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
