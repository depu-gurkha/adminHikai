package online.rkmhikai.model;

public class Resource {
    private int resourceId;
    private  String title;
    private String desc;
    private String file;// Uplaod in the response
    private String createdAt;
    private  int status;

    public Resource(int resourceId, String title, String desc, String file, String createdAt, int status) {
        this.resourceId = resourceId;
        this.title = title;
        this.desc = desc;
        this.file = file;
        this.createdAt = createdAt;
        this.status = status;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
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
