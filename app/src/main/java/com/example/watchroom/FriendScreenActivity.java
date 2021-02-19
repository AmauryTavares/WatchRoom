package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(FriendScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.friends_list_screen);

        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseUser> queryUsers = ParseQuery.getQuery("_User");
        ParseQuery<ParseObject> queryFriends = ParseQuery.getQuery("Friends");

        queryFriends.whereEqualTo("userId", currentUserId);

        ArrayList<String> myFriends = new ArrayList<>();
        ArrayList<String> friends = new ArrayList<>();
        AdapterUsersList adapter = new AdapterUsersList(this, friends);

        EditText search = (EditText) findViewById(R.id.search_friend);

        GridView friendsView = (GridView) findViewById(R.id.friends_list_view);
        List<ParseObject> objects = null;

        String[] search_tokens = search.getText().toString().split(" ");

        try {
            objects = queryFriends.find();
            for (int i = 0; i < objects.size(); i++) {
                String friendId = objects.get(i).getString("friendId");
                queryUsers.whereEqualTo("objectId", friendId);
                List<ParseUser> friendsObject = queryUsers.find();

                if (search.getText().toString().trim().isEmpty()){
                    for (int j = 0; j < friendsObject.size(); j++) {
                        String friendName = friendsObject.get(j).getString("username");
                        myFriends.add(friendName);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < myFriends.size(); i++) {
            adapter.add(myFriends.get(i));
        }


        ImageButton btn_search = (ImageButton) findViewById(R.id.btn_search);
        //ArrayList<String> friends_searched = new ArrayList<>();
        //AdapterUsersList adapter_searched = new AdapterUsersList(this, friends_searched);

        List<ParseObject> finalFriendsObject = objects;
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < finalFriendsObject.size(); i++) {
                    String friendId = finalFriendsObject.get(i).getString("friendId");
                    queryUsers.whereEqualTo("objectId", friendId);
                    List<ParseUser> friendsObject = null;
                    try {
                        friendsObject = queryUsers.find();
                        for (int j = 0; j < friendsObject.size(); j++) {
                            String friendName = friendsObject.get(j).getString("username");

                            if (friendName.equals(search.getText().toString())) {
                                System.out.println(friendName);
                                adapter.clear();
                                adapter.add(friendName);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        friendsView.setAdapter(adapter);
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }
}
