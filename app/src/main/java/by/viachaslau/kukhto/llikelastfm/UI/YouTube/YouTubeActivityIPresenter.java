package by.viachaslau.kukhto.llikelastfm.UI.YouTube;

import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;

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
