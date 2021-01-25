package com.example.watchroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterVideoList extends ArrayAdapter<Video> {
    public AdapterVideoList(Context context, ArrayList<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Video video = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.video_list_item, parent, false);
        }
        // Population Room
        TextView name = (TextView) convertView.findViewById(R.id.video_name);
        name.setText(video.getName());

        TextView time = (TextView) convertView.findViewById(R.id.description);
        time.setText(video.getDescription());

        TextView views = (TextView) convertView.findViewById(R.id.time_views);
        views.setText(video.getTime() + " - " + video.getViews() + " visualizações");

        // Return the completed view to render on screen
        return convertView;
    }
}
