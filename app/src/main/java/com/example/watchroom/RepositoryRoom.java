package com.example.watchroom;

import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.parse.Parse.getApplicationContext;

public class RepositoryRoom {
    private ParseObject roomObject = new ParseObject("Room");
    private ParseObject userRoomObject = new ParseObject("UserRoom");

    public void createRoom(Room room){
        roomObject.put("name", room.getName());
        roomObject.put("privacy", room.getPrivacy());
        roomObject.put("password", room.getPassword());

        if (room.getPrivacy().equals("Privada")) {
            roomObject.put("code", randomUUID(6));
        } else {
            roomObject.put("code", randomUUID(6));
        }

        roomObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                userRoomObject.put("UserId", ParseUser.getCurrentUser());
                userRoomObject.put("RoomId", roomObject);
                userRoomObject.put("IsAdmin", true);
                userRoomObject.saveInBackground();
            }
        });
    }

    public boolean DeleteRoom(Room room) {
        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");

        // Query Parameters
        query.whereEqualTo("objectId", room.getObjectId());

        try {
            List<ParseObject> objects = query.find();

            if (objects.size() > 0) {
                ParseObject roomObject = objects.get(0);

                // Configure Query
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserRoom");

                // Query Parameters
                query2.whereEqualTo("RoomId", roomObject);

                List<ParseObject> objects2 = query2.find();

                if (objects2.size() > 0) {
                    for (int i = 0; i < objects2.size(); i++) {
                        objects2.get(i).deleteInBackground();
                    }
                }

                roomObject.deleteInBackground();

                return true;
            }

        } catch (Exception e) {

        }

        return false;
    }

    public boolean OutRoom(Room room) {
        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");

        // Query Parameters
        query.whereEqualTo("objectId", room.getObjectId());

        try {
            List<ParseObject> objects = query.find();

            if (objects.size() > 0) {
                ParseObject roomObject = objects.get(0);

                // Configure Query
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserRoom");

                // Query Parameters
                query2.whereEqualTo("RoomId", roomObject);
                query2.whereEqualTo("UserId", ParseUser.getCurrentUser());

                List<ParseObject> objects2 = query2.find();

                if (objects2.size() > 0) {
                    if (objects2.get(0).getBoolean("IsAdmin")) {
                        Toast.makeText(getApplicationContext(), "O administrador não pode sair da sala.", Toast.LENGTH_SHORT).show();
                    } else {
                        objects2.get(0).deleteInBackground();
                        return true;
                    }
                }
            }

        } catch (Exception e) {

        }

        return false;
    }

    public Room joinRoom(String code, String password) {
        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");

        // Query Parameters
        query.whereEqualTo("code", code);
        query.whereEqualTo("password", password);

        try {
            List<ParseObject> objects = query.find();

            if (objects.size() > 0) {
                ParseObject roomObject = objects.get(0);
                Room room = new Room(roomObject.getString("name"),
                        getAdminName(roomObject),
                        roomObject.getString("privacy"),
                        "",
                        getNumberOfUsers(roomObject));

                room.setObjectId(roomObject.getObjectId());

                // Configure Query
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserRoom");

                // Query Parameters
                query2.whereEqualTo("RoomId", roomObject);
                query2.whereEqualTo("UserId", ParseUser.getCurrentUser());

                List<ParseObject> objects2 = query2.find();

                if (objects2.size() == 0) {
                    AddUser(room);
                }

                return room;
            } else {
                Toast.makeText(getApplicationContext(), "Sala não encontrada ou senha incorreta.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }

        return null;
    }

    public void AddUser(Room room) {
        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");

        // Query Parameters
        query.whereEqualTo("objectId", room.getObjectId());

        try {
            List<ParseObject> objects = query.find();

            userRoomObject.put("UserId", ParseUser.getCurrentUser());
            userRoomObject.put("RoomId", objects.get(0));
            userRoomObject.put("IsAdmin", false);
            userRoomObject.saveInBackground();
        } catch (Exception e) {

        }
    }

    public ArrayList<Room> getMyRooms(String search) {
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

                    newRoom.setObjectId(roomObject.getObjectId());

                    myRooms.add(newRoom);
                }
            }
        } catch (Exception e) {

        }

        return myRooms;
    }

    public ArrayList<Room> getPublicRooms(String search) {
        ArrayList<Room> myRooms = new ArrayList<>();

        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");

        try {
            List<ParseObject> objects = query.find();

            for (int i = 0; i < objects.size(); i++) {
                ParseObject roomObject = objects.get(i);

                // Configure Query
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserRoom");

                // Query Parameters
                query2.whereEqualTo("RoomId", roomObject);
                query2.whereEqualTo("UserId", ParseUser.getCurrentUser());

                List<ParseObject> objects2 = query2.find();

                if (objects2.size() == 0) {
                    if (roomObject.getString("privacy").toString().equals("Pública")
                            && (search.trim().isEmpty() || roomObject.getString("name").toString().matches(".*" + search + ".*"))) {
                        Room newRoom = new Room(roomObject.getString("name"),
                                getAdminName(roomObject),
                                roomObject.getString("privacy"),
                                "",
                                getNumberOfUsers(roomObject));

                        newRoom.setObjectId(roomObject.getObjectId());

                        myRooms.add(newRoom);
                    }
                }
            }
        } catch (Exception e) {

        }

        return myRooms;
    }

    private int getNumberOfUsers(ParseObject roomObject) {
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

    private String getAdminName (ParseObject roomObject) {
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

    static final private String ALPHABET = "0123456789";
    final private Random rng = new SecureRandom();

    char randomChar(){
        return ALPHABET.charAt(rng.nextInt(ALPHABET.length()));
    }

    private String randomUUID(int length){
        StringBuilder sb = new StringBuilder();
        while(length > 0){
            length--;
            sb.append(randomChar());
        }
        return sb.toString();
    }

}
