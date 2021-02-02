package com.example.watchroom;

public class UserRoom {

    private String userId;
    private String roomId;
    private boolean isAdmin;

    public UserRoom(String userId, String roomId, boolean isAdmin) {
        this.userId = userId;
        this.roomId = roomId;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
