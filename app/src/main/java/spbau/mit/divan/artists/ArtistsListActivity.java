package spbau.mit.divan.artists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ArtistsListActivity extends AppCompatActivity {
    private static final String ARTISTS_LIST_URL = "http://cache-spb01.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_list);
        ListView artistsList = (ListView) findViewById(R.id.artists_list);
        new SetArtistsListTask(this, artistsList, ARTISTS_LIST_URL).execute();
    }
}
