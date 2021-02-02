package com.example.watchroom;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String objectId;
    private String name;
    private String administrator;
    private String privacy;
    private int numberOfUsers;
    private String password;

    public Room(String name, String privacy, String password) {
        this.name = name;
        this.privacy = privacy;
        this.password = password;
    }

    public Room(String name, String administrator, String privacy, String password, int numberOfUsers) {
        this.name = name;
        this.administrator = administrator;
        this.privacy = privacy;
        this.numberOfUsers = numberOfUsers;
        this.password = password;
    }

    public static ArrayList<Room> getMyRooms(String search) {
        ArrayList<Room> myRooms = new ArrayList<>();

        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRoom");
        query.include("RoomId");
        // Query Parameters
        query.whereEqualTo("UserId", ParseUser.getCurrentUser());

        try {
            List<ParseObject> objects = query.find();

            for (int i = 0; i < objects.size(); i++) {
                ParseObject userRoom = objects.get(i);
                ParseObject roomObject = userRoom.getParseObject("RoomId");

                if (search.trim().isEmpty() || roomObject.getString("name").toString().matches(".*" + search + ".*")) {
                    Room newRoom = new Room(roomObject.getString("name"),
                            getAdminName(roomObject),
                            roomObject.getString("privacy"),
                            "",
                            getNumberOfUsers(roomObject));

                    myRooms.add(newRoom);
                }
            }
        } catch (Exception e) {

        }

        return myRooms;
    }

    public static ArrayList<Room> getPublicRooms(String search) {
        ArrayList<Room> myRooms = new ArrayList<>();

        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRoom");
        query.include("RoomId");
        // Query Parameters
        query.whereNotEqualTo("UserId", ParseUser.getCurrentUser());

        try {
            List<ParseObject> objects = query.find();

            for (int i = 0; i < objects.size(); i++) {
                ParseObject userRoom = objects.get(i);
                ParseObject roomObject = userRoom.getParseObject("RoomId");

                if (roomObject.getString("privacy").toString().equals("Pública")
                    && (search.trim().isEmpty() || roomObject.getString("name").toString().matches(".*" + search + ".*"))) {
                    Room newRoom = new Room(roomObject.getString("name"),
                            getAdminName(roomObject),
                            roomObject.getString("privacy"),
                            "",
                            getNumberOfUsers(roomObject));

                    myRooms.add(newRoom);
                }
            }
        } catch (Exception e) {

        }

        return myRooms;
    }

    private static int getNumberOfUsers(ParseObject roomObject) {
        int num = 0;

        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRoom");

        // Query Parameters
        query.whereEqualTo("RoomId", roomObject);

        try {
            List<ParseObject> objects = query.find();
            num = objects.size();
        } catch (Exception e) {

        }

        return num;
    }

    private static String getAdminName (ParseObject roomObject) {
        String adminName = "";

        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRoom");
        query.include("UserId");
        // Query Parameters
        query.whereEqualTo("RoomId", roomObject);
        query.whereEqualTo("IsAdmin", true);

        try {
            List<ParseObject> objects = query.find();

            ParseObject userId = objects.get(0).getParseObject("UserId");

            adminName = userId.getString("username").toString();

        } catch (Exception e) {

        }

        return adminName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
