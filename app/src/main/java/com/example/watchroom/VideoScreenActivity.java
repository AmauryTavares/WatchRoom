package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        LinearLayout topmb = (LinearLayout) findViewById(R.id.top_menu_button);

        topmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
                LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
                LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
                LinearLayout back = (LinearLayout) findViewById(R.id.top_menu_back);
                LinearLayout rm = (LinearLayout) findViewById(R.id.top_menu);

                tmb.setVisibility(View.INVISIBLE);
                lmb.setVisibility(View.INVISIBLE);
                rmb.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
                rm.setVisibility(View.VISIBLE);

                TextView tv = (TextView) findViewById(R.id.name_text);
                tv.requestFocus();

            }
        });

        LinearLayout back_top = (LinearLayout) findViewById(R.id.top_menu_back);

        back_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
                LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
                LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
                LinearLayout back_top = (LinearLayout) findViewById(R.id.top_menu_back);
                LinearLayout rm = (LinearLayout) findViewById(R.id.top_menu);

                tmb.setVisibility(View.VISIBLE);
                rmb.setVisibility(View.VISIBLE);
                lmb.setVisibility(View.VISIBLE);
                back_top.setVisibility(View.INVISIBLE);
                rm.setVisibility(View.INVISIBLE);

            }
        });

        LinearLayout leftmb = (LinearLayout) findViewById(R.id.left_menu_button);

        leftmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
                LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
                LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
                LinearLayout back = (LinearLayout) findViewById(R.id.left_menu_back);
                LinearLayout rm = (LinearLayout) findViewById(R.id.left_menu);

                tmb.setVisibility(View.INVISIBLE);
                lmb.setVisibility(View.INVISIBLE);
                rmb.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
                rm.setVisibility(View.VISIBLE);

                TextView tv = (TextView) findViewById(R.id.name_text);
                tv.requestFocus();

            }
        });

        LinearLayout back_left = (LinearLayout) findViewById(R.id.left_menu_back);

        back_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
                LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
                LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
                LinearLayout back_left = (LinearLayout) findViewById(R.id.left_menu_back);
                LinearLayout rm = (LinearLayout) findViewById(R.id.left_menu);

                tmb.setVisibility(View.VISIBLE);
                rmb.setVisibility(View.VISIBLE);
                lmb.setVisibility(View.VISIBLE);
                back_left.setVisibility(View.INVISIBLE);
                rm.setVisibility(View.INVISIBLE);

            }
        });

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

        Button btn_config = (Button) findViewById(R.id.btn_settings);

        btn_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RoomSettingsActivity.class);
                view.getContext().startActivity(intent);}
        });

        Button btn_out = (Button) findViewById(R.id.btn_out);

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
                view.getContext().startActivity(intent);}
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
