package spbau.mit.divan.artists;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static spbau.mit.divan.artists.Utils.*;
import static spbau.mit.divan.artists.Utils.getCoverFromArtist;

public class SetArtistsListTask extends AsyncTask<Void, Void, String> {
    String artistsListURL = null;
    ListView listView = null;
    String resultJson = "";
    Activity context = null;

    public SetArtistsListTask(Activity context, ListView listView, String artistsListURL) {
        this.listView = listView;
        this.artistsListURL = artistsListURL;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(artistsListURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultJson = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        final JSONArray dataJSONArr;
        try {
            dataJSONArr = new JSONArray(strJson);
            final List<ArtistInfo> artists = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                artists.add(parseArtistInfo(dataJSONArr.getJSONObject(i)));
            }
            listView.setAdapter(new ArtistsAdapter(context, artists));
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(context, ArtistPageActivity.class);
                try {
                    intent.putExtra("artist_details", dataJSONArr.getJSONObject(position).toString());
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            for (int i = 0; i < 10; i++) {
                final int finalI = i;
                new LoadImageTask(getCoverFromArtist(dataJSONArr.getJSONObject(i), "small"), cover ->
                        ((ArtistsAdapter )listView.getAdapter()).updateViewCover(finalI, cover)).execute();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArtistInfo parseArtistInfo(JSONObject artist) throws JSONException {
        String name = artist.getString("name");
        String genres = artist.getJSONArray("genres").toString();
        String amount = getDiscographyInfoFromArtist(artist);
        Drawable cover = ContextCompat.getDrawable(context, R.drawable.loading);
        return new ArtistInfo(name, genres, amount, cover);
    }
}
