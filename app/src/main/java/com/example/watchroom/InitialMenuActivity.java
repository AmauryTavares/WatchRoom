package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class InitialMenuActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_menu_screen);
    }

    public void MyRooms(View view) {
        Intent intent = new Intent(view.getContext(), MyRoomsScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void PublicRooms(View view) {
        Intent intent = new Intent(view.getContext(), PublicRoomsScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void CodeRoom(View view) {
        Intent intent = new Intent(view.getContext(), CodeRoomScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void CreateRoom(View view) {
        Intent intent = new Intent(view.getContext(), CreateRoomScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Profile(View view) {
        Intent intent = new Intent(view.getContext(), ProfileScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Friends(View view) {
        Intent intent = new Intent(view.getContext(), FriendScreenActivity.class);
        view.getContext().startActivity(intent);
    }
}
