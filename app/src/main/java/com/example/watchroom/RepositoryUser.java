package com.example.watchroom;

import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static com.parse.Parse.getApplicationContext;

public class RepositoryUser {
    ParseUser backUser = new ParseUser();
    public void addUser(User usuario){
        backUser.setUsername(usuario.username);
        backUser.setPassword(usuario.password);
        backUser.setEmail(usuario.email);
        backUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    ParseUser.logOut();
                }
            }
        });
    }
}
