package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import static com.parse.Parse.getApplicationContext;

public class RoomSettingsActivity extends Activity {
    EditText room_name;
    EditText room_password;
    Room room;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(RoomSettingsActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.settings_screen);

        Button btn_settings = (Button) findViewById(R.id.btn_back_settings);
        Button save_settings = (Button) findViewById(R.id.save_settings);

        room = (Room) getIntent().getSerializableExtra("Teste");
        room_name = (EditText) findViewById(R.id.name_settings);
        room_password = (EditText) findViewById(R.id.room_password_settings);

        room_name.setText(room.getName().toString(), TextView.BufferType.EDITABLE);
        room_password.setText(room.getPassword().toString(), TextView.BufferType.EDITABLE);

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        save_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSetting(view);
            }
        });
    }

    public void saveSetting(View view){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        ParseQuery<ParseObject> object = query.whereEqualTo("name", room.getName());
        query.getInBackground(room.getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject room, ParseException e) {
                if (e == null) {
                    room.put("name", room_name.getText().toString());
                    room.put("password", room_password.getText().toString());
                    room.saveInBackground();
                    Toast.makeText(getApplicationContext(), "Alterações salvas!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), MyRoomsScreenActivity.class);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }
}
