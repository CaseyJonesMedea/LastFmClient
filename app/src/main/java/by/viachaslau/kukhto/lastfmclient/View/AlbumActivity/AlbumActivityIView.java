package by.viachaslau.kukhto.lastfmclient.View.AlbumActivity;

import android.support.v4.app.Fragment;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;

/**
 * Created by VKukh on 04.03.2017.
 */

public interface AlbumActivityIView {

    void showLoadProgressBar();

    void hideLoadProgressBar();

    void showErrorFragment();

    void showFragment(Fragment fragment, boolean addToBackStack, String tag);

    void initAlbumFull(Album album);

}
