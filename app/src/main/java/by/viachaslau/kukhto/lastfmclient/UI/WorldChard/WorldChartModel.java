package by.viachaslau.kukhto.lastfmclient.UI.WorldChard;

import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public interface WorldChartModel {

    Observable getChartTopArtists();

    Observable getChartTopTracks();
}
