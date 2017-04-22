package by.viachaslau.kukhto.llikelastfm.UI.List;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import by.viachaslau.kukhto.llikelastfm.Others.Data;
import by.viachaslau.kukhto.llikelastfm.Others.Model.ModelImpl;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.PaginatedResult;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.User;
import rx.Observable;


/**
 * Created by kuhto on 27.03.2017.
 */

public class ListModelImpl extends ModelImpl implements ListModel {


    public ListModelImpl() {
        super();
    }

    @Override
    public Observable getRecentTracks(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            PaginatedResult<Track> result = User.getRecentTracks(name, 1, 100, Data.API_KEY);
            Collection<Track> collection = result.getPageResults();
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getTopArtists(String name) {
        Observable<List<Artist>> observable = Observable.fromCallable(() -> {
            Collection<Artist> collection = User.getTopArtists(name, Data.API_KEY);
            List<Artist> artists = new ArrayList<>(collection);
            return artists;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getTopAlbums(String name) {
        Observable<List<Album>> observable = Observable.fromCallable(() -> {
            Collection<Album> collection = User.getTopAlbums(name, Data.API_KEY);
            List<Album> albums = new ArrayList<>(collection);
            return albums;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getTopTracks(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            Collection<Track> collection = User.getTopTracks(name, Data.API_KEY);
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getLovedTracks(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            PaginatedResult<Track> result = User.getLovedTracks(name, Data.API_KEY);
            Collection<Track> collection = result.getPageResults();
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getSimilarArtists(String name) {
        Observable<List<Artist>> observable = Observable.fromCallable(() -> {
            Collection<Artist> collection = Artist.getSimilar(name, Data.API_KEY);
            List<Artist> artists = new ArrayList<>(collection);
            return artists;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getArtistTopAlbums(String name) {
        Observable<List<Album>> observable = Observable.fromCallable(() -> {
            Collection<Album> collection = Artist.getTopAlbums(name, Data.API_KEY);
            List<Album> albums = new ArrayList<>(collection);
            return albums;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getArtistTopTracks(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            Collection<Track> collection = Artist.getTopTracks(name, Data.API_KEY);
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }
}
