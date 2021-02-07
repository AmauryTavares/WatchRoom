package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

public class PublicRoomsScreenActivity extends Activity {
    ArrayList<Room> myRooms;
    RepositoryRoom repoRoom = new RepositoryRoom();
    GridView gridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(PublicRoomsScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.public_rooms_screen);
        ArrayList<Room> rooms = new ArrayList<>();
        AdapterPublicRoomList adapter = new AdapterPublicRoomList(this, rooms);

        myRooms = repoRoom.getPublicRooms("");

        for (int i = 0; i < myRooms.size(); i++) {
            adapter.add(myRooms.get(i));
        }
        gridView = (GridView) findViewById(R.id.rooms_list);
        gridView.setAdapter(adapter);

        TextView emptyView = (TextView)findViewById(R.id.empty);
        emptyView.setText("Nenhuma sala encontrada.");
        gridView.setEmptyView(emptyView);
    }

    public void Video(View view) {
        Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);

        int position = gridView.getPositionForView(view);

        intent.putExtra("Room", myRooms.get(position));

        repoRoom.AddUser(myRooms.get(position));

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

        ArrayList<Room> myRooms = repoRoom.getPublicRooms(search_text.getText().toString());

        for (int i = 0; i < myRooms.size(); i++) {
            adapter.add(myRooms.get(i));
        }
        final GridView gridView = (GridView) findViewById(R.id.rooms_list);
        gridView.setAdapter(adapter);

    }
}
