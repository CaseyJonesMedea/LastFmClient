package by.viachaslau.kukhto.lastfmclient.UI.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.ChartFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.FriendsFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.HomeFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Chart;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.PaginatedResult;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.User;
import rx.Observable;


/**
 * Created by kuhto on 28.03.2017.
 */

public class UserModelImpl extends ModelImpl implements UserModel {

    public UserModelImpl() {
        super();
    }

    @Override
    public Observable getUserInfo(String nameUser) {
        Observable<User> observable = Observable.fromCallable(() -> {
            User user = User.getInfo(nameUser, Data.API_KEY);
            return user;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getHomeFragmentInformation(String user) {
        Observable<HomeFragmentInformation> observable = Observable.fromCallable(() -> {
            PaginatedResult<Track> result = User.getRecentTracks(user, Data.API_KEY);
            Collection<Track> artists = result.getPageResults();
            List<Track> recentTracks = new ArrayList<>(artists);
            Collection<Artist> resultTopArtists = User.getTopArtists(user, Data.API_KEY);
            List<Artist> topArtists = new ArrayList<>(resultTopArtists);
            Collection<Album> resultTopAlbums = User.getTopAlbums(user, Data.API_KEY);
            List<Album> topAlbums = new ArrayList<>(resultTopAlbums);
            Collection<Track> resultTopTracks = User.getTopTracks(user, Data.API_KEY);
            List<Track> topTracks = new ArrayList<>(resultTopTracks);
            PaginatedResult<Track> resultLovedTracks = User.getLovedTracks(user, Data.API_KEY);
            Collection<Track> userLovedTracks = resultLovedTracks.getPageResults();
            List<Track> lovedTracks = new ArrayList<>(userLovedTracks);
            return new HomeFragmentInformation(recentTracks, topArtists, topAlbums, topTracks, lovedTracks);
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getChartFragmentInformation(String user) {
        Observable<ChartFragmentInformation> observable = Observable.fromCallable(() -> {
            Chart<Track> tracksChart = User.getWeeklyTrackChart(user, Data.API_KEY);
            Collection<Track> tracksCollection = tracksChart.getEntries();
            List<Track> tracks = new ArrayList<>(tracksCollection);
            return new ChartFragmentInformation(tracks);
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getFriendsFragmentInformation(String user) {
        Observable<FriendsFragmentInformation> observable = Observable.fromCallable(() -> {
            PaginatedResult<User> resultFriends = User.getFriends(user, Data.API_KEY);
            Collection<User> friendsCollection = resultFriends.getPageResults();
            List<User> friends = new ArrayList<>(friendsCollection);
            return new FriendsFragmentInformation(friends);
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }
}
