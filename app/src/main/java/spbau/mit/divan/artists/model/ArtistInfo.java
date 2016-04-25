package spbau.mit.divan.artists.model;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import spbau.mit.divan.artists.R;

import static spbau.mit.divan.artists.model.ArtistParser.getDiscographyFromArtist;
import static spbau.mit.divan.artists.model.ArtistParser.getGenresFromArtist;
import static spbau.mit.divan.artists.model.ArtistParser.getNameFromArtist;

/**
 * Keeps main information about the artist.
 */
public class ArtistInfo {
    private final String name; //artist name
    private final String genres;//Genres of an artist
    private final String amount;//Amount of tracks and albums of an artist
    private Drawable cover;//cover of an artist

    /**
     * Constructs a new ArtistInfo instance
     *
     * @param artist  JSONObject which keeps all information about the artist.
     * @param context Activity of an app.
     *                Used to have access to app resources.
     * @throws JSONException if there is a problem parsing {@param artist}.
     */
    public ArtistInfo(JSONObject artist, Activity context) throws JSONException {
        this.name = getNameFromArtist(artist);
        this.genres = getGenresFromArtist(artist);
        this.amount = getDiscographyFromArtist(artist);
        //Get loading picture from resources
        this.cover = ContextCompat.getDrawable(context, R.drawable.loading);
    }

    public String getName() {
        return name;
    }

    public String getGenres() {
        return genres;
    }

    public String getAmount() {
        return amount;
    }

    public Drawable getCover() {
        return cover;
    }

    /**
     * Used to update cover, when it's loaded from the Internet.
     *
     * @param cover New cover of an ArtistInfo instance.
     */
    public void updateCover(Drawable cover) {
        this.cover = cover;
    }
}
