package online.rkmhikai.model;

public class Batch {
    int id;
    int sessionID;
    String batchName;
    String batchID;
    String moduleName;
    String courseName;
    String batchStartTime;
    String batchEndTime;
    String batchNote;
    String batchInCharge;

    public Batch(){

    }

    public Batch(int id, int sessionID, String batchName, String batchID, String moduleName, String courseName, String batchStartTime, String batchEndTime, String batchNote,String batchInCharge) {
        this.id = id;
        this.sessionID = sessionID;
        this.batchName = batchName;
        this.batchID = batchID;
        this.moduleName = moduleName;
        this.courseName = courseName;
        this.batchStartTime = batchStartTime;
        this.batchEndTime = batchEndTime;
        this.batchNote = batchNote;
        this.batchInCharge=batchInCharge;
    }

    public String getBatchInCharge() {
        return batchInCharge;
    }

    public void setBatchInCharge(String batchInCharge) {
        this.batchInCharge = batchInCharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBatchStartTime() {
        return batchStartTime;
    }

    public void setBatchStartTime(String batchStartTime) {
        this.batchStartTime = batchStartTime;
    }

    public String getBatchEndTime() {
        return batchEndTime;
    }

    public void setBatchEndTime(String batchEndTime) {
        this.batchEndTime = batchEndTime;
    }

    public String getBatchNote() {
        return batchNote;
    }

    public void setBatchNote(String batchNote) {
        this.batchNote = batchNote;
    }
}
