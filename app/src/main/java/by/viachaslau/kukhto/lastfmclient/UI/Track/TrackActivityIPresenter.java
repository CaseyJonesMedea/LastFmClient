package by.viachaslau.kukhto.lastfmclient.UI.Track;

import android.content.Intent;

/**
 * Created by kuhto on 15.03.2017.
 */

public interface TrackActivityIPresenter {

    void onCreate(TrackActivityIView iView, Intent intent);

    void onDestroy();
}
