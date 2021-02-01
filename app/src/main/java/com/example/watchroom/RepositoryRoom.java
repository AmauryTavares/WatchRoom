package com.example.watchroom;

import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static com.parse.Parse.getApplicationContext;

public class RepositoryRoom {
    private ParseObject roomObject = new ParseObject("Room");
    public void createRoom(Room room){
        roomObject.put("name", room.getName());
        roomObject.put("privacy", room.getPrivacy());
        roomObject.put("password", room.getPassword());
        roomObject.saveInBackground();
    }
}
