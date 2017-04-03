package by.viachaslau.kukhto.lastfmclient.UI.Artist;

import android.content.Intent;

/**
 * Created by kuhto on 03.03.2017.
 */

public interface ArtistActivityIPresenter {

    void onCreate(ArtistActivityIView iView, Intent intent);

    void onBtnInfoClick();

    void onBtnLibraryClick();

    void onBtnUpdateClick();

    void onBtnShareClick();

    void onDestroy();
}
