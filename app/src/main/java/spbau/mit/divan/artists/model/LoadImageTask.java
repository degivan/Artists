package spbau.mit.divan.artists.model;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.annimon.stream.function.Consumer;

import java.io.IOException;

import static spbau.mit.divan.artists.model.Utils.EXCEPTIONS_TAG;
import static spbau.mit.divan.artists.model.Utils.getInputStreamFromURL;

/**
 * AsyncTask for loading image from URL and perform an action with loaded image.
 */
public class LoadImageTask extends AsyncTask<Void, Void, Drawable> {
    private final static String SRC_NAME = "src_name";

    //Link to load image from.
    private final String uri;
    //Action to perform with a loaded image.
    private final Consumer<Drawable> action;

    /**
     * Constructs new instance of LoadImageTask.
     */
    public LoadImageTask(String uri, Consumer<Drawable> action) {
        this.uri = uri;
        this.action = action;
    }

    @Override
    protected Drawable doInBackground(Void... params) {
        if (Data.getDrawable(uri) == null) { //if image isn't loaded already
            try {//Load image and save it in cache.
                Data.addDrawable(uri, Drawable.createFromStream(getInputStreamFromURL(uri), SRC_NAME));
            } catch (IOException e) {
                Log.d(EXCEPTIONS_TAG, "Cannot connect to URL and open InputStream correctly in Utils.getInputStreamFromURL, "
                        + "URI: "
                        + uri);
            }
        }
        return Data.getDrawable(uri);
    }

    @Override
    protected void onPostExecute(Drawable cover) {
        if (cover != null) {
            action.accept(cover);
        }
    }
}
