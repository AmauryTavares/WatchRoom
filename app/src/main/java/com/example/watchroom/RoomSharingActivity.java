package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import androidx.annotation.Nullable;

public class RoomSharingActivity extends Activity {
    Room currentRoom;
    String code;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(RoomSharingActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.sharing_screen);

        currentRoom = (Room) getIntent().getSerializableExtra("Room");
        Generate();

        Button btn_close = (Button) findViewById(R.id.btn_share_close);
        TextView code_text = (TextView) findViewById(R.id.code_text);
        code_text.setText(code);


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Close(view);
            }
        });

    }

    public void Close(View view) {
        Intent intent = new Intent(view.getContext(), MyRoomsScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Generate(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        query.whereEqualTo("objectId", currentRoom.getObjectId());
        try {
            List<ParseObject> roomObject = query.find();
            for (int i = 0; i < roomObject.size(); i++) {
                ParseObject userRoom = roomObject.get(i);
                code = userRoom.getString("code");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
