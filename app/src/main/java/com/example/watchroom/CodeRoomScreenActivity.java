package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.parse.ParseUser;

public class CodeRoomScreenActivity extends Activity {
    RepositoryRoom repoRoom = new RepositoryRoom();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(CodeRoomScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.room_code_screen);
    }

    public void Enter(View view) {
        Room room = null;

        EditText code = (EditText) findViewById(R.id.name_text);
        EditText password = (EditText) findViewById(R.id.password_text);

        if(code.getText().toString().trim().equals("") || password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else{
            room = repoRoom.joinRoom(code.getText().toString(), password.getText().toString());
        }

        if (room != null) {
            Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);
            intent.putExtra("Room", room);
            view.getContext().startActivity(intent);
        }
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }
}
