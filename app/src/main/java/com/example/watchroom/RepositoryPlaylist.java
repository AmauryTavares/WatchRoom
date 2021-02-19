package com.example.watchroom;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class RepositoryPlaylist {
    private ParseObject playlistObject = new ParseObject("Playlist");

    public void addVideoToPlaylist(String RoomId, String VideoId){
        playlistObject.put("VideoId", VideoId);
        playlistObject.put("RoomId", RoomId);

        playlistObject.saveInBackground();
    }

    public List<String> GetVideoIdsByRoomId(String roomId) {
        ArrayList<String> ids = new ArrayList<>();

        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Playlist");
        // Query Parameters
        query.whereEqualTo("RoomId", roomId);

        try {
            List<ParseObject> objects = query.find();

            for (int i = 0; i < objects.size(); i++) {
                ParseObject video = objects.get(i);

                ids.add(video.getString("VideoId"));
            }
        } catch (Exception e) {

        }

        return ids;
    }

    public boolean DeleteVideo(String videoId, String roomId) {
        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Playlist");

        // Query Parameters
        query.whereEqualTo("RoomId", roomId);
        query.whereEqualTo("VideoId", videoId);

        try {
            List<ParseObject> objects = query.find();

            objects.get(0).delete();

            return true;
        } catch (Exception e) {

        }

        return false;
    }
}
