package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VideoScreenActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_screen);

        LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);

        rmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
                LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
                LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu);
                LinearLayout back = (LinearLayout) findViewById(R.id.right_menu_back);
                LinearLayout rm = (LinearLayout) findViewById(R.id.right_menu);

                tmb.setVisibility(View.INVISIBLE);
                lmb.setVisibility(View.INVISIBLE);
                rmb.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
                rm.setVisibility(View.VISIBLE);

                TextView tv = (TextView) findViewById(R.id.name_text);
                tv.requestFocus();

            }
        });

        LinearLayout back = (LinearLayout) findViewById(R.id.right_menu_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
                LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
                LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
                LinearLayout back = (LinearLayout) findViewById(R.id.right_menu_back);
                LinearLayout rm = (LinearLayout) findViewById(R.id.right_menu);

                tmb.setVisibility(View.VISIBLE);
                rmb.setVisibility(View.VISIBLE);
                lmb.setVisibility(View.VISIBLE);
                back.setVisibility(View.INVISIBLE);
                rm.setVisibility(View.INVISIBLE);

            }
        });

        ArrayList<Video> videos = new ArrayList<>();
        AdapterVideoMenuList adapter = new AdapterVideoMenuList(this, videos);

        adapter.add(new Video("Vídeo 1", "7:30", "2.5 mi", ""));
        adapter.add(new Video("Vídeo 2","3:57", "1.3 mi", ""));
        adapter.add(new Video("Vídeo 3","3:32", "3 mil", ""));

        final GridView gridView = (GridView) findViewById(R.id.video_list);
        gridView.setAdapter(adapter);
    }

    public void SearchVideo(View view) {
        Intent intent = new Intent(view.getContext(), AddVideoScreenActivity.class);
        view.getContext().startActivity(intent);
    }

}
