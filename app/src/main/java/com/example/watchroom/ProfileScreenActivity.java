package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.widget.Button;
import android.widget.Toast;
=======
import android.widget.EditText;
import android.widget.TextView;
>>>>>>> 6a5fc49404d9e2a3e3f8df92d7e35878a5bbe6bf

import androidx.annotation.Nullable;

import com.parse.ParseUser;

public class ProfileScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(ProfileScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.profile_screen);

<<<<<<< HEAD
        Button btn_signout = (Button) findViewById(R.id.btn_signout);

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginScreenActivity.class));
            }
        });
=======
        TextView name = (TextView) findViewById(R.id.username_text);
        TextView email = (TextView) findViewById(R.id.email_text);

        name.setText(ParseUser.getCurrentUser().getUsername());
        email.setText(ParseUser.getCurrentUser().getEmail());
>>>>>>> 6a5fc49404d9e2a3e3f8df92d7e35878a5bbe6bf
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Logout(View view) {
        ParseUser.logOut();

        Intent intent = new Intent(view.getContext(), LoginScreenActivity.class);
        view.getContext().startActivity(intent);
    }
}
