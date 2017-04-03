package by.viachaslau.kukhto.lastfmclient.UI.Artist;

import android.content.Context;
import android.support.v4.app.Fragment;

import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;

/**
 * Created by kuhto on 03.03.2017.
 */

public interface ArtistActivityIView {

    void showLoadProgressBar();

    void hideLoadProgressBar();

    void showErrorFragment();

    void showFragment(Fragment fragment, boolean addToBackStack, String tag);

    void initArtistFull(Artist artist);

    Context getContext();
}
