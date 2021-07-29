package online.rkmhikai.model;

public class Assignment {
    private int assignmentId;
    private String title;
    private String desc;
    private String file;
    private String createdAt;
    private String submissionDate;
    private  int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Assignment(int assignmentId, String title, String desc, String file, String createdAt, String submissionDate, int status) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.desc = desc;
        this.file = file;
        this.createdAt = createdAt;
        this.submissionDate = submissionDate;
        this.status=status;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
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

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }
}
