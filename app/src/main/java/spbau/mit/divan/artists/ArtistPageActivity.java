package spbau.mit.divan.artists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static spbau.mit.divan.artists.Utils.*;

public class ArtistPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_page);
        try {
            JSONObject artist = new JSONObject(getIntent().getStringExtra("artist_details"));
            setTextToTextView(R.id.artist_name, artist.getString("name"));
            setTextToTextView(R.id.artist_genres, artist.getJSONArray("genres").toString());
            setTextToTextView(R.id.artist_description, artist.getString("description"));
            setTextToTextView(R.id.artist_discography, getDiscographyInfoFromArtist(artist));
            new LoadImageTask(getCoverFromArtist(artist, "big"), ((ImageView) findViewById(R.id.artistpage_img))::setImageDrawable).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setTextToTextView(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }
}
