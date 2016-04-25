package spbau.mit.divan.artists.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import spbau.mit.divan.artists.R;
import spbau.mit.divan.artists.model.LoadImageTask;
import spbau.mit.divan.artists.model.Utils;

import static spbau.mit.divan.artists.model.ArtistParser.ARTIST_DESCRIPTION;
import static spbau.mit.divan.artists.model.ArtistParser.ARTIST_NAME;
import static spbau.mit.divan.artists.model.ArtistParser.BIG_COVER_SIZE;
import static spbau.mit.divan.artists.model.ArtistParser.getCoverFromArtist;
import static spbau.mit.divan.artists.model.ArtistParser.getDiscographyFromArtist;
import static spbau.mit.divan.artists.model.ArtistParser.getGenresFromArtist;
import static spbau.mit.divan.artists.model.Utils.ARTIST_EXTRA;
import static spbau.mit.divan.artists.model.Utils.setTextToTextView;

/**
 * Activity with detailed information about the artist.
 * Opens on click in ArtistListActivity.
 */
public class ArtistPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_page);
        try {
            //Putting information about artist on Activity.
            JSONObject artist = new JSONObject(getIntent().getStringExtra(ARTIST_EXTRA));
            setTextToTextView(R.id.artist_name, artist.getString(ARTIST_NAME), this);
            setTextToTextView(R.id.artist_genres, getGenresFromArtist(artist), this);
            setTextToTextView(R.id.artist_description, artist.getString(ARTIST_DESCRIPTION), this);
            setTextToTextView(R.id.artist_discography, getDiscographyFromArtist(artist), this);
            //Starting new AsyncTask to load a big size cover of an artist.
            new LoadImageTask(getCoverFromArtist(artist, BIG_COVER_SIZE),
                    ((ImageView) findViewById(R.id.artistpage_img))::setImageDrawable).execute();
        } catch (JSONException e) {
            Log.d(Utils.EXCEPTIONS_TAG, "Cannot parse JSON in ArtistPageActivity.onCreate");
        }
    }
}
