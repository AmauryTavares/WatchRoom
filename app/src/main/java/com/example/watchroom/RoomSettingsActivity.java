package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.parse.ParseUser;

public class RoomSettingsActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(RoomSettingsActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.settings_screen);
        Button btn_add_user = (Button) findViewById(R.id.btn_back_settings);

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);
                view.getContext().startActivity(intent);}
        });
    }
}
