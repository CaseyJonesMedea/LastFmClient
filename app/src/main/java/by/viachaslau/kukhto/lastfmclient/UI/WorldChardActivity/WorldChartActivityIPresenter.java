package by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity;

/**
 * Created by kuhto on 07.03.2017.
 */

public interface WorldChartActivityIPresenter {

    void onCreate(WorldChartActivityIView iView);

    void onBtnUpdateClick();

    void onBtnChartArtistClick();

    void onBtnChartTracksClick();

    void onDestroy();
}
