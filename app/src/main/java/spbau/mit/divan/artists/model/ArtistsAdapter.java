package spbau.mit.divan.artists.model;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import spbau.mit.divan.artists.R;

/**
 * Custom adapter for artists list view.
 */
public class ArtistsAdapter extends BaseAdapter {
    //List of ArtistInfo instances to display on ListView instance.
    private List<ArtistInfo> list;
    //Activity with ListView on it.
    private Activity context;

    /**
     * Constructs new instance of ArtistsAdapter.
     */
    public ArtistsAdapter(Activity context, List<ArtistInfo> artists) {
        super();
        this.context = context;
        list = artists;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Class which keeps views from ListView row.
     */
    private class ViewHolder {
        TextView topTxtView;
        TextView midTxtView;
        TextView botTxtView;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.artistslistitem_row, null);
            holder = new ViewHolder();
            holder.topTxtView = (TextView) convertView.findViewById(R.id.text1);
            holder.midTxtView = (TextView) convertView.findViewById(R.id.text2);
            holder.botTxtView = (TextView) convertView.findViewById(R.id.text3);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.topTxtView.setText(list.get(position).getName());
        holder.midTxtView.setText(list.get(position).getGenres());
        holder.botTxtView.setText(list.get(position).getAmount());
        holder.img.setImageDrawable(list.get(position).getCover());

        return convertView;
    }

    /**
     * Used for update cover of artist row, when it's loaded.
     *
     * @param position Position of row to update.
     * @param cover    New artist cover.
     */
    public void updateViewCover(int position, Drawable cover) {
        if (position < list.size()) {
            list.get(position).updateCover(cover);
            notifyDataSetChanged();
        }
    }
}
