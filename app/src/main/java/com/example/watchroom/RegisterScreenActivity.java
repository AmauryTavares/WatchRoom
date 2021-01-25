package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;

import java.util.Objects;

public class RegisterScreenActivity extends Activity {
    private PopupWindow popupWindow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
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
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    public void AgreePrivacy(View view) {
        Intent intent = new Intent(view.getContext(), LoginScreenActivity.class);
        view.getContext().startActivity(intent);
    }

    public void DisagreePrivacy(View view) {
        popupWindow.dismiss();
    }

    public void Register(View view) {
        onButtonShowPopupWindowClick(view);
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), LoginScreenActivity.class);
        view.getContext().startActivity(intent);
    }
}
