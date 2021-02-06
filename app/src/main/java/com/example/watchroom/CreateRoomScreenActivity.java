package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.parse.ParseUser;

public class CreateRoomScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(CreateRoomScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.new_room_screen);
        EditText nameRoom = (EditText) findViewById(R.id.room_name);
        EditText passwordRoom = (EditText) findViewById(R.id.room_password);
        Button privacyRoom = (Button) findViewById(R.id.privacy_text);
        Button createRoomBtn = (Button) findViewById(R.id.create_button);

        RepositoryRoom repositorio = new RepositoryRoom();

        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameRoom.getText().toString().equals("") || passwordRoom.getText().toString().equals("") || privacyRoom.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }else{
                    //Room room = new Room(nameRoom.getText().toString(), "Admin", privacyRoom.getText().toString(), passwordRoom.getText().toString(), 1);
                    Room room = new Room(nameRoom.getText().toString(), privacyRoom.getText().toString(), passwordRoom.getText().toString());
                    repositorio.createRoom(room);
                    startActivity(new Intent(getApplicationContext(), InitialMenuActivity.class));
                }
            }
        });
    }

    public void Enter(View view) {
        Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }
}
