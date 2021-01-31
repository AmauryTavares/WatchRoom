package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.ParseObject;

import androidx.annotation.Nullable;

public class LoginScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }


    public void Login(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Register(View view) {
        Intent intent = new Intent(view.getContext(), RegisterScreenActivity.class);
        view.getContext().startActivity(intent);
    }
}
