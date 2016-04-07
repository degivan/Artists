package spbau.mit.divan.artists;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.annimon.stream.function.Consumer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImageTask extends AsyncTask<Void, Void, Drawable>{
    private String uri;
    private Consumer<Drawable> action;

    public LoadImageTask(String uri, Consumer<Drawable> action) {
        this.uri = uri;
        this.action = action;
    }

    @Override
    protected Drawable doInBackground(Void... params) {
        try {
            URL url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            return Drawable.createFromStream(inputStream, "src_name");
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable cover) {
        action.accept(cover);
    }
}
