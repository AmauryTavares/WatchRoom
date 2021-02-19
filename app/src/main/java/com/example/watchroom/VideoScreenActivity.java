package com.example.watchroom;

import com.example.watchroom.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.VideoListResponse;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VideoScreenActivity extends Activity {
    RepositoryRoom repoRoom = new RepositoryRoom();
    RepositoryUser repoUser = new RepositoryUser();
    RepositoryPlaylist repoPlaylist = new RepositoryPlaylist();
    Room currentRoom;
    List<String> videoIds;

    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /** Global instance of Youtube object to make all API requests. */
    private static YouTube youtube;

    @SuppressLint("StaticFieldLeak")
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

        final GridView gridView = (GridView) findViewById(R.id.video_list);
        TextView emptyView = (TextView)findViewById(R.id.empty);
        emptyView.setText("Nenhum vídeo adicionado.");
        gridView.setEmptyView(emptyView);

        getListOfVideo();
    }

    @SuppressLint("StaticFieldLeak")
    private void getListOfVideo() {
        ArrayList<Video> videos = new ArrayList<>();
        AdapterVideoMenuList adapter = new AdapterVideoMenuList(getApplicationContext(), videos);

        videoIds = repoPlaylist.GetVideoIdsByRoomId(currentRoom.getObjectId());

        new AsyncTask<Void, Void, AdapterVideoMenuList>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected AdapterVideoMenuList doInBackground(Void... voids) {
                youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("youtube-cmdline-search-sample").build();

                String apiKey = getString(R.string.youtube_key);

                Iterator<String> iteratorVideoIds = videoIds.iterator();;

                while (iteratorVideoIds.hasNext()) {
                    try {
                        YouTube.Videos.List videoRequest = youtube.videos().list("snippet,statistics,contentDetails");
                        videoRequest.setId(iteratorVideoIds.next());
                        videoRequest.setKey(apiKey);
                        VideoListResponse listResponse = videoRequest.execute();
                        List<com.google.api.services.youtube.model.Video> videoList = listResponse.getItems();

                        com.google.api.services.youtube.model.Video targetVideo = videoList.iterator().next();

                        Thumbnail thumbnail = (Thumbnail) targetVideo.getSnippet().getThumbnails().get("default");

                        adapter.add(new Video(Video.TruncateText(targetVideo.getSnippet().getTitle(), 13),
                                Video.ConverttoHHMMSS(targetVideo.getContentDetails().getDuration()),
                                Video.ConvertViewsCount(targetVideo.getStatistics().getViewCount()),
                                "",
                                thumbnail.getUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return adapter;
            }

            @Override
            protected void onPostExecute(AdapterVideoMenuList adapterVideoMenuList) {
                final GridView gridView = (GridView) findViewById(R.id.video_list);

                gridView.setAdapter(adapterVideoMenuList);
            }

        }.execute();
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

        GridView gridView = (GridView) findViewById(R.id.video_list);
        gridView.requestFocus();
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

        GridView gridView = (GridView) findViewById(R.id.video_list);
        gridView.requestFocus();
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

        EditText searchText = (EditText) findViewById(R.id.name_text);

        intent.putExtra("SearchText", searchText.getText().toString());
        intent.putExtra("Room", (Room) getIntent().getSerializableExtra("Room"));

        view.getContext().startActivity(intent);
    }

    public void DeleteVideo (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Desfazendo a sala");
        builder.setMessage("Você irá desfazer a sala. Deseja continuar?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                GridView gridView = (GridView) findViewById(R.id.video_list);

                int pos = gridView.getPositionForView(view);

                boolean result = repoPlaylist.DeleteVideo(videoIds.get(pos), currentRoom.getObjectId());

                if (result) {
                    Toast.makeText(getApplicationContext(), "Vídeo removido.", Toast.LENGTH_SHORT).show();
                    getListOfVideo();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao remover o vídeo.", Toast.LENGTH_SHORT).show();
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

}
