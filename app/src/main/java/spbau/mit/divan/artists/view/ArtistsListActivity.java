package spbau.mit.divan.artists.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;

import spbau.mit.divan.artists.R;
import spbau.mit.divan.artists.model.Data;
import spbau.mit.divan.artists.model.SetArtistsListTask;
import spbau.mit.divan.artists.model.Utils;

import static spbau.mit.divan.artists.model.Utils.displayToast;
import static spbau.mit.divan.artists.model.Utils.setTextToTextView;

/**
 * Activity with the list of artists from given URL.
 */
public class ArtistsListActivity extends AppCompatActivity {
    //URL to get list of artists from.
    private static final String ARTISTS_LIST_URL = "http://cache-spb01.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
    //ListView to display list of artists on.
    private ListView artistsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_list);
        artistsList = (ListView) findViewById(R.id.artists_list);
        //Starting new AsyncTask to load list of the artists and put information to ListView.
        new SetArtistsListTask(this, artistsList, ARTISTS_LIST_URL).execute();
    }

    //Action performed on "Back" button(id:back_button) click.
    public void onBackClick(View view) {
        int currentPage = Data.getCurrentPage();
        if (currentPage != 1) { //if current page isn't first
            onOtherPageClick(currentPage - 1);
        } else {
            displayToast("There is no previous page.", this); //show
        }
    }

    //Action performed on "Forward" button(id:forward_button) click.
    public void onForwardClick(View view) {
        int currentPage = Data.getCurrentPage();
        if (currentPage != Data.getPagesAmount()) { //if current page isn't last
            onOtherPageClick(currentPage + 1);
        } else {
            ((Button) findViewById(R.id.forward_button)).setEnabled(false);
        }
    }

    /**
     * Method called to switch to a different page  of a list.
     *
     * @param pageNumber number of a page to switch.
     */
    private void onOtherPageClick(int pageNumber) {
        try {
            Data.setCurrentPage(pageNumber); //change current page number in data store
            Utils.setCurrentPage(Data.getArtistArr(), pageNumber, this, artistsList); //change list of artists displayed to new
            setTextToTextView(R.id.currentPage, Integer.toString(pageNumber), this); //show the user that page number is changed
        } catch (JSONException e) {
            Log.d(Utils.EXCEPTIONS_TAG, "Cannot parse JSON in Utils.setCurrentPage");
        }
    }
}
