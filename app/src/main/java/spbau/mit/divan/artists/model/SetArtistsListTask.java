package spbau.mit.divan.artists.model;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static spbau.mit.divan.artists.model.Utils.EXCEPTIONS_TAG;
import static spbau.mit.divan.artists.model.Utils.getInputStreamFromURL;

/**
 * AsyncTask for loading list of artists from URL and put first ten artists on a ListView.
 */
public class SetArtistsListTask extends AsyncTask<Void, Void, JSONArray> {
    //URL to load list of artists from.
    private String artistsListURL = null;
    //ListView for first ten artists.
    private ListView listView = null;
    //Activity containing listView.
    private Activity context = null;

    /**
     * Constructs a SetArtistListTask instance.
     */
    public SetArtistsListTask(Activity context, ListView listView, String artistsListURL) {
        this.listView = listView;
        this.artistsListURL = artistsListURL;
        this.context = context;
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        if (Data.getArtistArr() == null) {//if no information about artists was already loaded.
            try {//Load information from given URL.
                InputStream inputStream = getInputStreamFromURL(artistsListURL);
                //Reading from InputStream line by line to String instance(variable called "str").
                String str;
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                str = buffer.toString();
                //Create an JSONArray instance from str.
                JSONArray jar = new JSONArray(str);
                Data.setArtistArr(jar); //save artist list to cache.
                Data.setPagesAmount(jar.length() / 10 + 1); //save amount of pages to cache.
            } catch (IOException e) {
                Log.d(EXCEPTIONS_TAG, "Problem connecting to given URL in SetArtistListTask.doInBackground method.");
            } catch (JSONException e) {
                Log.d(EXCEPTIONS_TAG, "Cannot parse JSON in SetArtistListTask.doInBackground method.");
            }
        }
        return Data.getArtistArr();
    }

    /**
     * Checks that information about artists was loaded
     * and displays information about first ten on listView.
     *
     * @param dataJSONArr list of artists as JSONArray.
     */
    @Override
    protected void onPostExecute(JSONArray dataJSONArr) {
        try {
            Utils.setCurrentPage(dataJSONArr, 1, context, listView); //Sets list of displayed artists to first ten from dataJSONArr
        } catch (JSONException e) {
            Log.d(EXCEPTIONS_TAG, "Cannot parse JSON in Utils.setCurrentPage method.");
        }
    }
}
