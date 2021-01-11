package com.example.watchroom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import java.util.List;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    int current = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int[] screens = {
                R.layout.loading_screen,
                R.layout.register_screen,
                R.layout.popup_privacy,
                R.layout.login_screen,
                R.layout.initial_menu_screen,
                R.layout.my_rooms_screen,
                R.layout.new_room_screen,
                R.layout.public_rooms_screen,
                R.layout.room_code_screen,
                R.layout.video_screen,
                R.layout.video_list_screen,
        };

        if (event.getAction() == KeyEvent.ACTION_UP) {
            Log.e("TAG", "code: " + event.getKeyCode());
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                current = Math.max(current - 1, 0);
                setContentView(screens[current]);
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                current = Math.min(current + 1, screens.length);
                setContentView(screens[current]);
            }
        }

        return true;
    }

}