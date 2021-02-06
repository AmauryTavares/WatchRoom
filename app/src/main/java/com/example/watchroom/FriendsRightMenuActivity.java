package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import com.parse.ParseUser;

public class FriendsRightMenuActivity extends Activity {
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(FriendsRightMenuActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.add_user_screen);

        ArrayList<String> arrayOfUsers = new ArrayList<String>();
        AdapterFriendsList adapter = new AdapterFriendsList(this, arrayOfUsers);

        adapter.add("Amigo 1");
        adapter.add("Amigo 2");
        adapter.add("Amigo 3");
        adapter.add("Amigo 4");

        ListView listView = (ListView) findViewById(R.id.friends_list_menu);
        listView.setAdapter(adapter);

        Button btn_add_user = (Button) findViewById(R.id.btn_back);

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);
                view.getContext().startActivity(intent);}
        });
    }
}
