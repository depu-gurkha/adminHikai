package online.rkmhikai.model;

public class School {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String pincode;
    private String email;
    private String management;
    private int  status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School(int id, String address, String phone, String pincode, String email, String management, int status, String name) {
        this.id = id;
        this.status=status;
        this.address = address;
        this.phone = phone;
        this.pincode = pincode;
        this.email = email;
        this.management = management;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management;
    }
}
