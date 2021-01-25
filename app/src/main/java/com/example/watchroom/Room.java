package com.example.watchroom;

public class Room {

    private String name;
    private String administrator;
    private String privacy;
    private int numberOfUsers;

    public Room(String name, String administrator, String privacy, int numberOfUsers) {
        this.name = name;
        this.administrator = administrator;
        this.privacy = privacy;
        this.numberOfUsers = numberOfUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
}
