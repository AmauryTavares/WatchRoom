package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyRoomsScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_rooms_screen);

        ArrayList<Room> rooms = new ArrayList<>();
        AdapterMyRoomList adapter = new AdapterMyRoomList(this, rooms);

        adapter.add(new Room("Room 1", "Adm 1", "Pública", 30));
        adapter.add(new Room("Room 2", "Adm 2", "Pública", 25));
        adapter.add(new Room("Room 3", "Adm 3", "Pública", 12));
        adapter.add(new Room("Room 4", "Adm 4", "Privada", 17));
        adapter.add(new Room("Room 5", "Adm 5", "Privada", 27));
        adapter.add(new Room("Room 6", "Adm 6", "Pública", 3));

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
}
