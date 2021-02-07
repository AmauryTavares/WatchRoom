package com.example.watchroom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.parse.ParseUser;

import java.util.ArrayList;

public class VideoScreenActivity extends Activity {
    RepositoryRoom repoRoom = new RepositoryRoom();
    RepositoryUser repoUser = new RepositoryUser();
    Room currentRoom;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(VideoScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.video_screen);

        TextView roomNameText = (TextView) findViewById(R.id.room_name_text);
        TextView videoNameText = (TextView) findViewById(R.id.video_name_text);

        currentRoom = (Room) getIntent().getSerializableExtra("Room");

        roomNameText.setText(currentRoom.getName());

        Button deleteButton = (Button) findViewById(R.id.btn_delete);
        LinearLayout menu_button = (LinearLayout) findViewById(R.id.back_button);
        Button settingsButton = (Button) findViewById(R.id.btn_settings);

        if (repoUser.IsAdmin(currentRoom) == false) {
            deleteButton.setVisibility(View.INVISIBLE);
            menu_button.setNextFocusRightId(R.id.btn_settings);
            settingsButton.setNextFocusLeftId(R.id.back_button);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
            menu_button.setNextFocusRightId(R.id.btn_delete);
            settingsButton.setNextFocusLeftId(R.id.btn_delete);
        }

        ArrayList<Video> videos = new ArrayList<>();
        AdapterVideoMenuList adapter = new AdapterVideoMenuList(this, videos);

        adapter.add(new Video("Vídeo 1", "7:30", "2.5 mi", ""));
        adapter.add(new Video("Vídeo 2","3:57", "1.3 mi", ""));
        adapter.add(new Video("Vídeo 3","3:32", "3 mil", ""));

        final GridView gridView = (GridView) findViewById(R.id.video_list);
        gridView.setAdapter(adapter);
    }

    public void Delete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Desfazendo a sala");
        builder.setMessage("Você irá desfazer a sala. Deseja continuar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                boolean sucess = repoRoom.DeleteRoom(currentRoom);

                if (sucess) {
                    Toast.makeText(getApplicationContext(), "A sala foi desfeita.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
                    view.getContext().startActivity(intent);
                }

            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void Menu(View view) {
        Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Settings(View view) {
        Intent intent = new Intent(view.getContext(), RoomSettingsActivity.class);
        view.getContext().startActivity(intent);
    }

    public void Out(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Saindo da sala");
        builder.setMessage("Você irá sair da sala. Deseja continuar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                boolean sucess = repoRoom.OutRoom(currentRoom);

                if (sucess) {
                    Toast.makeText(getApplicationContext(), "Você saiu da sala.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), InitialMenuActivity.class);
                    view.getContext().startActivity(intent);
                }

            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void BackRightMenu(View view) {
        LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
        LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
        LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
        LinearLayout back = (LinearLayout) findViewById(R.id.right_menu_back);
        LinearLayout rm = (LinearLayout) findViewById(R.id.right_menu);
        LinearLayout rn = (LinearLayout) findViewById(R.id.room_name);
        LinearLayout vn = (LinearLayout) findViewById(R.id.video_name);

        tmb.setVisibility(View.VISIBLE);
        rmb.setVisibility(View.VISIBLE);
        lmb.setVisibility(View.VISIBLE);
        back.setVisibility(View.INVISIBLE);
        rm.setVisibility(View.INVISIBLE);
        rn.setVisibility(View.VISIBLE);
        vn.setVisibility(View.VISIBLE);

        rmb.requestFocus();
    }

    public void BackLeftMenu(View view) {
        LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
        LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
        LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
        LinearLayout back_left = (LinearLayout) findViewById(R.id.left_menu_back);
        LinearLayout rm = (LinearLayout) findViewById(R.id.left_menu);
        LinearLayout rn = (LinearLayout) findViewById(R.id.room_name);
        LinearLayout vn = (LinearLayout) findViewById(R.id.video_name);

        tmb.setVisibility(View.VISIBLE);
        rmb.setVisibility(View.VISIBLE);
        lmb.setVisibility(View.VISIBLE);
        back_left.setVisibility(View.INVISIBLE);
        rm.setVisibility(View.INVISIBLE);
        rn.setVisibility(View.VISIBLE);
        vn.setVisibility(View.VISIBLE);

        lmb.requestFocus();
    }

    public void BackTopMenu(View view) {
        LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
        LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
        LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
        LinearLayout back_top = (LinearLayout) findViewById(R.id.top_menu_back);
        LinearLayout rm = (LinearLayout) findViewById(R.id.top_menu);
        LinearLayout rn = (LinearLayout) findViewById(R.id.room_name);
        LinearLayout vn = (LinearLayout) findViewById(R.id.video_name);

        tmb.setVisibility(View.VISIBLE);
        rmb.setVisibility(View.VISIBLE);
        lmb.setVisibility(View.VISIBLE);
        back_top.setVisibility(View.INVISIBLE);
        rm.setVisibility(View.INVISIBLE);
        rn.setVisibility(View.VISIBLE);
        vn.setVisibility(View.VISIBLE);

        tmb.requestFocus();
    }

    public void LeftMenu(View view) {
        LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
        LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
        LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
        LinearLayout back = (LinearLayout) findViewById(R.id.left_menu_back);
        LinearLayout rm = (LinearLayout) findViewById(R.id.left_menu);
        LinearLayout rn = (LinearLayout) findViewById(R.id.room_name);
        LinearLayout vn = (LinearLayout) findViewById(R.id.video_name);

        tmb.setVisibility(View.INVISIBLE);
        lmb.setVisibility(View.INVISIBLE);
        rmb.setVisibility(View.INVISIBLE);
        back.setVisibility(View.VISIBLE);
        rm.setVisibility(View.VISIBLE);
        rn.setVisibility(View.INVISIBLE);
        vn.setVisibility(View.INVISIBLE);

        TextView tv = (TextView) findViewById(R.id.name_text);
        tv.requestFocus();
    }

    public void RightMenu(View view) {
        LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
        LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
        LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu);
        LinearLayout back = (LinearLayout) findViewById(R.id.right_menu_back);
        LinearLayout rm = (LinearLayout) findViewById(R.id.right_menu);
        LinearLayout rn = (LinearLayout) findViewById(R.id.room_name);
        LinearLayout vn = (LinearLayout) findViewById(R.id.video_name);

        tmb.setVisibility(View.INVISIBLE);
        lmb.setVisibility(View.INVISIBLE);
        rmb.setVisibility(View.INVISIBLE);
        back.setVisibility(View.VISIBLE);
        rm.setVisibility(View.VISIBLE);
        rn.setVisibility(View.INVISIBLE);
        vn.setVisibility(View.INVISIBLE);

        TextView tv = (TextView) findViewById(R.id.name_text);
        tv.requestFocus();
    }

    public void TopMenu(View view) {
        LinearLayout tmb = (LinearLayout) findViewById(R.id.top_menu_button);
        LinearLayout lmb = (LinearLayout) findViewById(R.id.left_menu_button);
        LinearLayout rmb = (LinearLayout) findViewById(R.id.right_menu_button);
        LinearLayout back = (LinearLayout) findViewById(R.id.top_menu_back);
        LinearLayout rm = (LinearLayout) findViewById(R.id.top_menu);
        LinearLayout rn = (LinearLayout) findViewById(R.id.room_name);
        LinearLayout vn = (LinearLayout) findViewById(R.id.video_name);

        tmb.setVisibility(View.INVISIBLE);
        lmb.setVisibility(View.INVISIBLE);
        rmb.setVisibility(View.INVISIBLE);
        back.setVisibility(View.VISIBLE);
        rm.setVisibility(View.VISIBLE);
        rn.setVisibility(View.INVISIBLE);
        vn.setVisibility(View.INVISIBLE);

        LinearLayout tv = (LinearLayout) findViewById(R.id.back_button);
        tv.requestFocus();
    }

    public void SearchVideo(View view) {
        Intent intent = new Intent(view.getContext(), AddVideoScreenActivity.class);
        view.getContext().startActivity(intent);
    }

}
