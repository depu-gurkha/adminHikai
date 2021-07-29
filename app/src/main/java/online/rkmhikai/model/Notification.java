package online.rkmhikai.model;



public class Notification {

    private int id;
    private String title;
    private String text;
    private String imgNotice;

    public Notification(int id, String title, String text) {
        this.title = title;
        this.text = text;
        this.id=id;

    }

    public Notification(String title, String text, String imgNotice) {
        this.title = title;
        this.text = text;
        this.imgNotice = imgNotice;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgNotice() { return imgNotice; }

    public void setImgNotice(String imgNotice) { this.imgNotice = imgNotice; }
}
