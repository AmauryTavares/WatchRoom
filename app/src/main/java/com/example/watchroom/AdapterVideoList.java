package com.example.watchroom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AdapterVideoList extends ArrayAdapter<Video> {
    public AdapterVideoList(Context context, ArrayList<Video> videos) {
        super(context, 0, videos);
    }

    @SuppressLint("StaticFieldLeak")
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

        View finalConvertView = convertView;

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    URL urlConnection = new URL(video.getUrlThumb());
                    HttpURLConnection connection = (HttpURLConnection) urlConnection
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    return myBitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                ImageView thumb = (ImageView) finalConvertView.findViewById(R.id.video_img);
                thumb.setImageBitmap(result);
            }

        }.execute();

        // Return the completed view to render on screen
        return convertView;
    }
}
