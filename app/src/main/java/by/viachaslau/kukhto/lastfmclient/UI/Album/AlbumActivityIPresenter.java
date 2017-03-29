package by.viachaslau.kukhto.lastfmclient.UI.Album;

import android.content.Intent;

/**
 * Created by VKukh on 04.03.2017.
 */

public interface AlbumActivityIPresenter {

    void onCreate(AlbumActivityIView iView, Intent intent);

    void onBtnUpdateClick();

    void onBtnShareClick();

    void onDestroy();
}
