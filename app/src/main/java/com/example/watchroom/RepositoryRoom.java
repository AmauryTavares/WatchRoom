package com.example.watchroom;

import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import static com.parse.Parse.getApplicationContext;

public class RepositoryRoom {
    private ParseObject roomObject = new ParseObject("Room");
    private ParseObject userRoomObject = new ParseObject("UserRoom");
    public void createRoom(Room room){
        roomObject.put("name", room.getName());
        roomObject.put("privacy", room.getPrivacy());
        roomObject.put("password", room.getPassword());
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
}
