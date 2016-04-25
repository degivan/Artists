package spbau.mit.divan.artists.model;

import android.graphics.drawable.Drawable;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to cache loaded data and number of current page in list.
 */
public class Data {
    //number of a current opened page of a list.
    private static int currentPage = 1;
    //List of information about artists as JSONArray.
    private static JSONArray artistArr = null;
    //Map of already loaded pictures. Keys are links, values are loaded from.
    private static Map<String, Drawable> drawableMap = new HashMap<>();
    //Amount of list pages.
    private static int pagesAmount = 0;

    public static void setCurrentPage(int currentPage) {
        Data.currentPage = currentPage;
    }

    public static int getCurrentPage() {
        return currentPage;
    }

    public static void setArtistArr(JSONArray artistArr) {
        Data.artistArr = artistArr;
    }

    public static JSONArray getArtistArr() {
        return artistArr;
    }

    /**
     * Add drawable to cache.
     *
     * @param uri      Link, from which {@param drawable} is loaded.
     * @param drawable Picture to save in cache.
     */
    public static void addDrawable(String uri, Drawable drawable) {
        drawableMap.put(uri, drawable);
    }

    public static Drawable getDrawable(String uri) {
        return drawableMap.get(uri);
    }

    public static void setPagesAmount(int pagesAmount) {
        Data.pagesAmount = pagesAmount;
    }

    public static int getPagesAmount() {
        return pagesAmount;
    }
}