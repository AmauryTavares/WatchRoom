package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendScreenActivity extends Activity {
    String currentUserId;
    ParseQuery<ParseUser> queryUsers;
    ParseQuery<ParseObject> queryFriends;
    List<ParseObject> objects = null;
    ArrayList<String> myFriends;
    EditText search;
    AdapterUsersList adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(FriendScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.friends_list_screen);

        currentUserId = ParseUser.getCurrentUser().getObjectId();
        queryUsers = ParseQuery.getQuery("_User");
        queryFriends = ParseQuery.getQuery("Friends");

        queryFriends.whereEqualTo("userId", currentUserId);

        myFriends = new ArrayList<>();
        ArrayList<String> friends = new ArrayList<>();
        adapter = new AdapterUsersList(this, friends);

        search = (EditText) findViewById(R.id.search_friend);

        GridView friendsView = (GridView) findViewById(R.id.friends_list_view);
        Get();

        ImageButton btn_search = (ImageButton) findViewById(R.id.btn_search);
        ImageButton btn_remove = (ImageButton) findViewById(R.id.btn_rmv_friend);

        List<ParseObject> finalFriendsObject = objects;
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search(adapter, search, queryUsers, queryFriends, finalFriendsObject);
            }
        });

        friendsView.setAdapter(adapter);
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Search(AdapterUsersList adapter, EditText search, ParseQuery<ParseUser> queryUsers, ParseQuery<ParseObject> queryFriends, List<ParseObject> finalFriendsObject){
        if(!search.getText().toString().trim().isEmpty()){
            for (int i = 0; i < finalFriendsObject.size(); i++) {
                String friendId = finalFriendsObject.get(i).getString("friendId");
                queryUsers.whereEqualTo("objectId", friendId);
                List<ParseUser> friendsObject = null;
                try {
                    friendsObject = queryUsers.find();
                    for (int j = 0; j < friendsObject.size(); j++) {
                        String friendName = friendsObject.get(j).getString("username").toLowerCase();

                        if (friendName.contains(search.getText().toString().toLowerCase())) {
                            adapter.clear();
                            adapter.add(friendName);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }else{
            adapter.clear();
            adapter.notifyDataSetChanged();
            TextView emptyView = (TextView) findViewById(R.id.empty_friends);
            emptyView.setText("Nenhuma amigo encontrada.");
        }
    }

    public void Get(){
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
    }
}
