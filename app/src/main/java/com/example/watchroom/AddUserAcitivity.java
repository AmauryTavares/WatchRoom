package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class AddUserAcitivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(AddUserAcitivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.room_left_menu_screen);
        Button btn_add_user = (Button) findViewById(R.id.btn_add_user);

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FriendsRightMenuActivity.class);
                view.getContext().startActivity(intent);}
        });
    }
}
