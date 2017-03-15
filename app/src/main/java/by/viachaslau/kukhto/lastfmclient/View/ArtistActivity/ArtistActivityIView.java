package by.viachaslau.kukhto.lastfmclient.View.ArtistActivity;

import android.support.v4.app.Fragment;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Artist;

/**
 * Created by kuhto on 03.03.2017.
 */

public interface ArtistActivityIView {

    void showLoadProgressBar();

    void hideLoadProgressBar();

    void showErrorFragment();

    void showFragment(Fragment fragment, boolean addToBackStack, String tag);

    void initArtistFull(Artist artist);
}
