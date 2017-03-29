package by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Chart;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.PaginatedResult;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public class WorldChartModelImpl extends ModelImpl implements WorldChartModel {

    public WorldChartModelImpl() {
        super();
    }

    @Override
    public Observable getChartTopArtists() {
        Observable<List<Artist>> observable = Observable.fromCallable(() -> {
            PaginatedResult<Artist> result = Chart.getTopArtists(Data.API_KEY);
            Collection<Artist> collection = result.getPageResults();
            List<Artist> artists = new ArrayList<>(collection);
            return artists;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getChartTopTracks() {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            PaginatedResult<Track> result = Chart.getTopTracks(Data.API_KEY);
            Collection<Track> collection = result.getPageResults();
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }
}
