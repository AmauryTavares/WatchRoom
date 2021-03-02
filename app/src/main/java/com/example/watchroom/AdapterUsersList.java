package com.example.watchroom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.parse.ParseUser;

import java.util.ArrayList;

public class AdapterUsersList extends ArrayAdapter<String> {
    RepositoryUser repo;
    public AdapterUsersList(Context context, ArrayList<String> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String friend = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_left_menu_item, parent, false);
        }

        //repo = new RepositoryUser();
        //ParseUser currentUser = ParseUser.getCurrentUser();

        // Lookup view for data population
        @SuppressLint("WrongViewCast") Button tvName = (Button) convertView.findViewById(R.id.text_left_menu_item);
        // Populate the data into the template view using the data object
        tvName.setText(friend);
        ImageButton btn_remove = (ImageButton) convertView.findViewById(R.id.btn_rmv_friend);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //repo.removeFriend(friend, currentUser);
                remove(friend);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
