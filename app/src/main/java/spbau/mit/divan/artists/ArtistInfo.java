package spbau.mit.divan.artists;

import android.graphics.drawable.Drawable;

public class ArtistInfo {
    private String name;
    private String genres;
    private String amount;
    private Drawable cover;

    public ArtistInfo(String name, String genres, String amount, Drawable cover) {
        this.name = name;
        this.genres = genres;
        this.amount = amount;
        this.cover = cover;
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

    public void updateCover(Drawable cover) {
        this.cover = cover;
    }
}
