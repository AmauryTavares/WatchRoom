package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.parse.ParseUser;

import java.util.ArrayList;

public class MyRoomsScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(MyRoomsScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.my_rooms_screen);

        ArrayList<Room> rooms = new ArrayList<>();
        AdapterMyRoomList adapter = new AdapterMyRoomList(this, rooms);

        ArrayList<Room> myRooms = Room.getMyRooms("");

        for (int i = 0; i < myRooms.size(); i++) {
            adapter.add(myRooms.get(i));
        }

        final GridView gridView = (GridView) findViewById(R.id.rooms_list);
        gridView.setAdapter(adapter);
    }

    public void Video(View view) {
        Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Search(View view) {
        ArrayList<Room> rooms = new ArrayList<>();
        AdapterPublicRoomList adapter = new AdapterPublicRoomList(this, rooms);

        EditText search_text = (EditText) findViewById(R.id.name_text);

        ArrayList<Room> myRooms = Room.getMyRooms(search_text.getText().toString());

        for (int i = 0; i < myRooms.size(); i++) {
            adapter.add(myRooms.get(i));
        }
        final GridView gridView = (GridView) findViewById(R.id.rooms_list);
        gridView.setAdapter(adapter);

    }
}
