package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LoginScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }

    public void Login(View view) {
        RepositoryUser repoUser = new RepositoryUser();

        EditText email = (EditText) findViewById(R.id.email_text);
        EditText password = (EditText) findViewById(R.id.password_text);

        if(email.getText().toString().trim().equals("") || password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else{
            repoUser.login(email.getText().toString(), password.getText().toString(), view);
        }
    }

    public void Register(View view) {
        Intent intent = new Intent(view.getContext(), RegisterScreenActivity.class);
        view.getContext().startActivity(intent);
    }
}
