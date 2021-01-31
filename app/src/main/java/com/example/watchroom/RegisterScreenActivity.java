package com.example.watchroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Objects;

import static android.widget.Toast.LENGTH_LONG;

public class RegisterScreenActivity extends Activity {
    private PopupWindow popupWindow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        EditText nomeUsuario = (EditText) findViewById(R.id.name_text);
        EditText senhaUsuario = (EditText) findViewById(R.id.password_text);
        EditText senhaUsuarioConfimacao = (EditText) findViewById(R.id.conf_password_text);
        EditText emailUsuario = (EditText) findViewById(R.id.email_text);

        Button register_button = (Button) findViewById(R.id.register_button);

        RepositoryUser repositorio = new RepositoryUser();

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nomeUsuario.getText().toString().equals("") || senhaUsuario.getText().toString().equals("") || senhaUsuarioConfimacao.getText().toString().equals("") || emailUsuario.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }else if(!senhaUsuario.getText().toString().equals(senhaUsuarioConfimacao.getText().toString())){
                    Toast.makeText(getApplicationContext(), "As senhas digitadas não são compatíveis.", Toast.LENGTH_LONG).show();
                }else{
                    User usuario = new User(nomeUsuario.getText().toString(), senhaUsuario.getText().toString(), emailUsuario.getText().toString());
                    repositorio.addUser(usuario);
                    startActivity(new Intent(getApplicationContext(), LoginScreenActivity.class));
                }
            }
        });
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
