package online.rkmhikai.model;

public class Users {
    private int userId;
    private int participantId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String UserType;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String userActive;
    private String permission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getParticipantId() {
        return participantId;
    }


    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public Users(int userId, int participantId, String firstName, String lastName, String email, String phoneNo, String userType, String userActive, String permission) {
        this.userId = userId;
        this.participantId = participantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        UserType = userType;
        this.userActive = userActive;
        this.permission = permission;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserActive() {
        return userActive;
    }

    public void setUserActive(String userActive) {
        this.userActive = userActive;
    }
}
