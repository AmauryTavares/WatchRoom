package com.example.watchroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterPublicRoomList extends ArrayAdapter<Room> {
    public AdapterPublicRoomList(Context context, ArrayList<Room> rooms) {
        super(context, 0, rooms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Room room = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.public_room_item, parent, false);
        }
        // Population Room
        TextView name1 = (TextView) convertView.findViewById(R.id.room_name1);
        name1.setText(room.getName());

        TextView admin1 = (TextView) convertView.findViewById(R.id.adm_name1);
        admin1.setText("Administrador: " + room.getAdministrator());

        TextView users1 = (TextView) convertView.findViewById(R.id.users1);
        users1.setText(String.valueOf(room.getNumberOfUsers()));

        // Return the completed view to render on screen
        return convertView;
    }
}
