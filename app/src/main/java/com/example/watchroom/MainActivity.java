package com.example.watchroom;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    int current = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_video_list);
//register
//        Button bt = (Button) findViewById(R.id.register_button);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onButtonShowPopupWindowClick(v);
//            }
//        });


 //video
//        LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
//
//        rmb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
//                LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
//                LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu);
//                LinearLayout back = (LinearLayout) findViewById(R.id.right_menu_back);
//                LinearLayout rm = (LinearLayout) findViewById(R.id.right_menu);
//
//                tmb.setVisibility(View.INVISIBLE);
//                lmb.setVisibility(View.INVISIBLE);
//                rmb.setVisibility(View.INVISIBLE);
//                back.setVisibility(View.VISIBLE);
//                rm.setVisibility(View.VISIBLE);
//
//                TextView tv = (TextView) findViewById(R.id.name_text);
//                tv.requestFocus();
//
//            }
//        });
//
//        LinearLayout back = (LinearLayout) findViewById(R.id.right_menu_back);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
//                LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
//                LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
//                LinearLayout back = (LinearLayout) findViewById(R.id.right_menu_back);
//                LinearLayout rm = (LinearLayout) findViewById(R.id.right_menu);
//
//                tmb.setVisibility(View.VISIBLE);
//                rmb.setVisibility(View.VISIBLE);
//                lmb.setVisibility(View.VISIBLE);
//                back.setVisibility(View.INVISIBLE);
//                rm.setVisibility(View.INVISIBLE);
//
//            }
//        });

    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_privacy, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//
//        int[] screens = {
//                R.layout.add_video_list,
//                R.layout.loading_screen,
//                R.layout.register_screen,
//                R.layout.popup_privacy,
//                R.layout.login_screen,
//                R.layout.initial_menu_screen,
//                R.layout.my_rooms_screen,
//                R.layout.new_room_screen,
//                R.layout.public_rooms_screen,
//                R.layout.room_code_screen,
//                R.layout.video_screen,
//                R.layout.video_list_screen,
//        };
//
//        if (event.getAction() == KeyEvent.ACTION_UP) {
//            Log.e("TAG", "code: " + event.getKeyCode());
//            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
//                current = Math.max(current - 1, 0);
//                setContentView(screens[current]);
//            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
//                current = Math.min(current + 1, screens.length);
//                setContentView(screens[current]);
//            }
//        }
//
//        return true;
//    }

}