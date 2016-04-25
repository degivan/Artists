package spbau.mit.divan.artists.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class with methods used to get varied information about artist from JSONObject.
 */
public class ArtistParser {
    //These constants are used to parse JSONObject, which keeps info about the artist.
    public static final String ARTIST_NAME = "name";
    public static final String ARTIST_GENRES = "genres";
    public static final String ARTIST_DESCRIPTION = "description";
    public static final String ARTIST_COVER = "cover";
    public static final String ARTIST_TRACKS = "tracks";
    public static final String ARTIST_ALBUMS = "albums";
    public static final String BIG_COVER_SIZE = "big";
    public static final String SMALL_COVER_SIZE = "small";

    /**
     * Gets name of an artist from an artist info.
     *
     * @param artist Information about artist as JSONObject.
     * @return name of an artist.
     * @throws JSONException if there is no field "name" in {@param artist}
     */
    public static String getNameFromArtist(JSONObject artist) throws JSONException {
        return artist.getString(ARTIST_NAME);
    }

    /**
     * Gets link to a cover from an artist info.
     *
     * @param artist Information about artist as JSONObject.
     * @param size   Size of a cover to load.Can have two values: "big" and "small".
     * @return Link to load cover.
     * @throws JSONException if there is a problem parsing {@param artist}
     */
    public static String getCoverFromArtist(JSONObject artist, String size) throws JSONException {
        return artist.getJSONObject(ARTIST_COVER).getString(size);
    }

    /**
     * Gets genres from an artist info.
     *
     * @param artist Information about artist as JSONObject
     * @return Genres as String instance. Separated by commas.
     * @throws JSONException if there is a problem parsing {@param artist}
     */
    public static String getGenresFromArtist(JSONObject artist) throws JSONException {
        return artist.getJSONArray(ARTIST_GENRES).join(", ");
    }

    /**
     * Gets discography information from an artist info.
     *
     * @param artist Information about artist as JSONObject
     * @return Amount of tracks and albums as a String.
     * @throws JSONException if there is a problem parsing {@param artist}
     */
    public static String getDiscographyFromArtist(JSONObject artist) throws JSONException {
        return Integer.toString(artist.getInt(ARTIST_TRACKS))
                + " песен, "
                + Integer.toString(artist.getInt(ARTIST_ALBUMS))
                + " альбомов";
    }
}
