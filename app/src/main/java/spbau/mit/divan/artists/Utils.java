package spbau.mit.divan.artists;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    public static String getCoverFromArtist(JSONObject artist, String size) throws JSONException {
        return artist.getJSONObject("cover").getString(size);
    }

    public static String getDiscographyInfoFromArtist(JSONObject artist) throws JSONException {
        return Integer.toString(artist.getInt("tracks")) + " песен, " + Integer.toString(artist.getInt("albums")) + " альбомов";
    }
}
