package com.example.watchroom;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

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

    public boolean IsAdmin(Room room) {
        // Configure Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");

        // Query Parameters
        query.whereEqualTo("objectId", room.getObjectId());

        try {
            List<ParseObject> objects = query.find();

            if (objects.size() > 0) {
                ParseObject roomObject = objects.get(0);

                // Configure Query
                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserRoom");

                // Query Parameters
                query2.whereEqualTo("RoomId", roomObject);
                query2.whereEqualTo("UserId", ParseUser.getCurrentUser());
                query2.whereEqualTo("IsAdmin", true);

                List<ParseObject> objects2 = query2.find();

                if (objects2.size() > 0) {
                    return true;
                }
            }

        } catch (Exception e) {

        }

        return false;
    }

    public void login(String email, String password, View view) {
        backUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    Toast.makeText(getApplicationContext(), "Bem vindo(a), " + parseUser.getUsername() + ".", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
                    view.getContext().startActivity(intent);
                } else {
                    ParseUser.logOut();
                    Toast.makeText(getApplicationContext(), "Email ou senha inválido.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void removeFriend(String friend, ParseUser user){
        //System.out.println(user.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
        query.whereEqualTo("userId", user.getObjectId());
        try {
            List<ParseObject> friends = query.find();
            for (int i = 0; i < friends.size(); i++) {
                ParseObject friendObject = friends.get(i);
                String objectId = friendObject.getString("friendId");

                ParseQuery<ParseObject> queryUser = ParseQuery.getQuery("_User");
                queryUser.whereEqualTo("userId", objectId);
                queryUser.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> object, ParseException e) {
                        if (e == null) {
                            System.out.println(object);
                        }
                    };
                });
                /*
                List<ParseUser> frnUsers = queryUser.find();
                for (int j = 0; j < frnUsers.size(); j++) {
                    ParseUser friendUser = frnUsers.get(i);
                    System.out.println(friendUser.getString("username"));
                }

                 */
            }
        } catch (Exception e) {

        }

    }
}
