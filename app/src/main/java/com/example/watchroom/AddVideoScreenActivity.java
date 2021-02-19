package com.example.watchroom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

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
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVideoScreenActivity extends Activity {
    /** Global instance properties filename. */
    private static String PROPERTIES_FILENAME = "youtube.properties";

    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /** Global instance of the max number of videos we want returned (50 = upper limit per page). */
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    /** Global instance of Youtube object to make all API requests. */
    private static YouTube youtube;

    private Iterator<SearchResult> iteratorSearchResults;

    RepositoryPlaylist repoPlaylist;

    List<String> videoIds;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // verify authetication
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            Intent intent = new Intent(AddVideoScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.add_video_list);

        repoPlaylist = new RepositoryPlaylist();

        final GridView gridView = (GridView) findViewById(R.id.videos_list);
        TextView emptyView = (TextView)findViewById(R.id.empty);
        emptyView.setText("Buscando vídeos...");
        gridView.setEmptyView(emptyView);

        SearchVideoOnYoutube("");
    }

    @SuppressLint("StaticFieldLeak")
    private void SearchVideoOnYoutube(String searchText) {
        final GridView gridView = (GridView) findViewById(R.id.videos_list);
        ArrayList<Video> videos = new ArrayList<>();
        AdapterVideoList clearAdapter = new AdapterVideoList(getApplicationContext(), videos);
        gridView.setAdapter(clearAdapter);

        videoIds = new ArrayList<>();

        new AsyncTask<Void, Void, AdapterVideoList>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected AdapterVideoList doInBackground(Void... voids) {
                /**
                 * Initializes YouTube object to search for videos on YouTube (Youtube.Search.List). The program
                 * then prints the names and thumbnails of each of the videos (only first 50 videos).
                 *
                 * @param args command line args.
                 */
                try {
                    /*
                     * The YouTube object is used to make all API requests. The last argument is required, but
                     * because we don't need anything initialized when the HttpRequest is initialized, we override
                     * the interface and provide a no-op function.
                     */
                    youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                        public void initialize(HttpRequest request) throws IOException {
                        }
                    }).setApplicationName("youtube-cmdline-search-sample").build();

                    // Get query term from user.
                    String queryTerm = "";
                    if (searchText.equals("")) {
                        queryTerm = getIntent().getStringExtra("SearchText");
                    } else {
                        queryTerm = searchText;
                    }

                    YouTube.Search.List search = youtube.search().list("id,snippet");
                    /*
                     * It is important to set your API key from the Google Developer Console for
                     * non-authenticated requests (found under the Credentials tab at this link:
                     * console.developers.google.com/). This is good practice and increased your quota.
                     */
                    String apiKey = getString(R.string.youtube_key);
                    search.setKey(apiKey);
                    search.setQ(queryTerm);
                    /*
                     * We are only searching for videos (not playlists or channels). If we were searching for
                     * more, we would add them as a string like this: "video,playlist,channel".
                     */
                    search.setType("video");
                    /*
                     * This method reduces the info returned to only the fields we need and makes calls more
                     * efficient.
                     */
                    search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
                    search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
                    SearchListResponse searchResponse = search.execute();

                    List<SearchResult> searchResultList = searchResponse.getItems();

                    iteratorSearchResults = searchResultList.iterator();

                    ArrayList<Video> videos = new ArrayList<>();
                    AdapterVideoList adapter = new AdapterVideoList(getApplicationContext(), videos);

                    while (iteratorSearchResults.hasNext()) {

                        SearchResult singleVideo = iteratorSearchResults.next();
                        ResourceId rId = singleVideo.getId();
                        Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

                        // Double checks the kind is video.
                        if (rId.getKind().equals("youtube#video")) {
                            try {
                                YouTube.Videos.List videoRequest = youtube.videos().list("snippet,statistics,contentDetails");
                                videoRequest.setId(rId.getVideoId());
                                videoRequest.setKey(apiKey);
                                VideoListResponse listResponse = videoRequest.execute();
                                List<com.google.api.services.youtube.model.Video> videoList = listResponse.getItems();

                                com.google.api.services.youtube.model.Video targetVideo = videoList.iterator().next();

                                adapter.add(new Video(Video.TruncateText(targetVideo.getSnippet().getTitle(), 40),
                                        Video.ConverttoHHMMSS(targetVideo.getContentDetails().getDuration()),
                                        Video.ConvertViewsCount(targetVideo.getStatistics().getViewCount()),
                                        Video.TruncateText(targetVideo.getSnippet().getDescription(), 255),
                                        thumbnail.getUrl()));

                                videoIds.add(rId.getVideoId());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    return adapter;

                } catch (GoogleJsonResponseException e) {
                    System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                            + e.getDetails().getMessage());
                } catch (IOException e) {
                    System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
                } catch (Throwable t) {
                    t.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(AdapterVideoList adapterVideoList) {
                final GridView gridView = (GridView) findViewById(R.id.videos_list);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), VideoScreenActivity.class);

                        Room room = (Room) getIntent().getSerializableExtra("Room");

                        repoPlaylist.addVideoToPlaylist(room.getObjectId(), videoIds.get(position));

                        intent.putExtra("Room", room);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        view.getContext().startActivity(intent);
                    }
                });

                gridView.setAdapter(adapterVideoList);
            }

        }.execute();
    }

    public void Back(View view) {
        Intent intent = new Intent(view.getContext(), Video.class);

        intent.putExtra("Room", (Room) getIntent().getSerializableExtra("Room"));

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        view.getContext().startActivity(intent);
    }

    public void SearchVideoOnYoutube(View view) {
        EditText searchText = (EditText) findViewById(R.id.search_text);
        SearchVideoOnYoutube(searchText.getText().toString());
    }
}
