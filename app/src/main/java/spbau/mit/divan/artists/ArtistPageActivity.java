package spbau.mit.divan.artists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ArtistPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_page);
        try {
            JSONObject artist = new JSONObject(getIntent().getStringExtra("artist_details"));
            ((TextView) findViewById(R.id.artist_name)).setText(artist.getString("name"));
            ((TextView) findViewById(R.id.artist_genres)).setText(artist.getJSONArray("genres").toString());
            ((TextView) findViewById(R.id.artist_description)).setText(artist.getString("description"));
            new LoadImageTask(artist.getJSONObject("cover").getString("big"), ((ImageView) findViewById(R.id.artistpage_img))::setImageDrawable).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
