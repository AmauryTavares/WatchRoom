package com.example.watchroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterVideoMenuList extends ArrayAdapter<Video> {
    public AdapterVideoMenuList(Context context, ArrayList<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Video video = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.video_side_menu_item, parent, false);
        }
        // Population Room
        TextView name = (TextView) convertView.findViewById(R.id.video_name);
        name.setText(video.getName());

        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(video.getTime());

        TextView views = (TextView) convertView.findViewById(R.id.views);
        views.setText(video.getViews() + " visualizações");

        // Return the completed view to render on screen
        return convertView;
    }
}
