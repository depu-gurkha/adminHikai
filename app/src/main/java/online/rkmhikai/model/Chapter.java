package online.rkmhikai.model;


import java.util.List;

public class Chapter {
    private String chapterTitle;
    private int totalLecture;
    private int chapterNo;
    private String description;
    private List<Content> contentList;

    public Chapter() {

    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public int getTotalLecture() {
        return totalLecture;
    }

    public void setTotalLecture(int totalLecture) {
        this.totalLecture = totalLecture;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }
}
