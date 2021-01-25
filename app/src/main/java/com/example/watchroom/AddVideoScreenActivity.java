package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AddVideoScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_video_list);

        ArrayList<Video> videos = new ArrayList<>();
        AdapterVideoList adapter = new AdapterVideoList(this, videos);

        adapter.add(new Video("Vídeo 1","7:30", "2.5 mi", "Description 1"));
        adapter.add(new Video("Vídeo 2","3:57", "1.3 mi", "Description 2"));
        adapter.add(new Video("Vídeo 3","3:32", "3 mil", "Description 3"));

        final GridView gridView = (GridView) findViewById(R.id.videos_list);
        gridView.setAdapter(adapter);
    }

    public void AddVideo(View view) {
        Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }
}
