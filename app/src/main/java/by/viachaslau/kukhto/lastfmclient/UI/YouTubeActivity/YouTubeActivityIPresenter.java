package by.viachaslau.kukhto.lastfmclient.UI.YouTubeActivity;

import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;

/**
 * Created by VKukh on 19.03.2017.
 */

public interface YouTubeActivityIPresenter {

    void onCreate(YouTubeActivityIView iView, String code, Track track);

    void onBtnShareClick();

    void onBtnLoveClick();

    void scrobbleTrack();

    void onDestroy();

}
