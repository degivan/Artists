package spbau.mit.divan.artists.model;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import spbau.mit.divan.artists.view.ArtistPageActivity;

import static spbau.mit.divan.artists.model.ArtistParser.SMALL_COVER_SIZE;
import static spbau.mit.divan.artists.model.ArtistParser.getCoverFromArtist;

public class Utils {
    //String to recognize information about artist being put to Intent instance.
    public static final String ARTIST_EXTRA = "artist_details";
    //Tag used for logging in case of exception.
    public static final String EXCEPTIONS_TAG = "Exceptions";

    /**
     * Finds TextView by {@param id} and sets {@param text} to it.
     *
     * @param id      id of an TextView.
     * @param text    text to put on TextView.
     * @param context Activity to get access to app resources.
     */
    public static void setTextToTextView(int id, String text, Activity context) {
        ((TextView) context.findViewById(id)).setText(text);
    }

    /**
     * Displays toast on Android screen for a short time.
     *
     * @param text    Text to display on screen.
     * @param context Activity to get access to Android resources.
     */
    public static void displayToast(String text, Activity context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Connects to URL and gets information from it as input stream.
     *
     * @param stringURL Link represented as a String instance.
     * @return InputStream as a result of  HTTP connection to URL created from {@param stringURL}.
     * @throws IOException if there is connection problem, URL is malformed or protocol problem.
     */
    public static InputStream getInputStreamFromURL(String stringURL) throws IOException {
        URL url = new URL(stringURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        return urlConnection.getInputStream();
    }

    /**
     * Gets ten artists which should be on page with number {@param currentPage}
     * and displays short info about them on listView. Also sets on artist info click listener,
     * which is opening activity with more detailed info about artist.
     *
     * @param dataJSONArr List of information about artists.
     * @param currentPage Number of page to display.
     * @param context     Activity instance contains listView.
     *                    Allows to get access to Android resources.
     * @param listView    ListView instance
     * @throws JSONException if there is a problem parsing {@param dataJSONArr}
     */
    public static void setCurrentPage(JSONArray dataJSONArr, int currentPage, Activity context, ListView listView) throws JSONException {
        if (dataJSONArr == null) {//if no information about artists was loaded.
            displayToast("Cannot load list of artists from URL: check your internet connection.", context); //notify user of problem
        } else {
            final List<ArtistInfo> artists = new ArrayList<>(); //Create a list of ArtistInfo instances.
            int startPosition = currentPage * 10 - 10; //index of first artist on page.
            int finalPosition = getFinalPosition(dataJSONArr, currentPage); //index of next to last artist on page.

            for (int i = startPosition; i < finalPosition; i++) {
                artists.add(new ArtistInfo(dataJSONArr.getJSONObject(i), context)); //Parse and add i-th artist information to "artists".
                final int position = i - startPosition; //position of i-th artist in listView
                //Starts AsyncTask to load and put small cover of an artist in row
                new LoadImageTask(getCoverFromArtist(dataJSONArr.getJSONObject(i), SMALL_COVER_SIZE), cover ->
                        ((ArtistsAdapter) listView.getAdapter()).updateViewCover(position, cover)).execute();
            }
            listView.setAdapter(new ArtistsAdapter(context, artists)); //Sets adapter, created from ArtistInfo list we fill before, to listView.
            listView.setOnItemClickListener((parent, view, position, id) -> {
                //Opens activity with more detailed information about artist.
                Intent intent = new Intent(context, ArtistPageActivity.class);
                try {
                    intent.putExtra(ARTIST_EXTRA, dataJSONArr.getJSONObject(startPosition + position).toString());
                    context.startActivity(intent);
                } catch (JSONException e) {
                    Log.d(EXCEPTIONS_TAG, "Cannot parse JSONArray in listView.setOnItemClickListener, position: "
                            + position
                            + ", page: "
                            + currentPage);
                }
            });
        }
    }

    /**
     * Selects last position, info from which we should parse.
     *
     * @param dataJSONArr List of information about artists as JSONArray instance.
     * @param currentPage Number of current page.
     * @return last position of {@param dataJSONArr}, from which we should read artist info.
     */
    private static int getFinalPosition(JSONArray dataJSONArr, int currentPage) {
        if (dataJSONArr.length() > currentPage * 10) {
            return currentPage * 10;
        } else {
            return dataJSONArr.length();
        }
    }


}
