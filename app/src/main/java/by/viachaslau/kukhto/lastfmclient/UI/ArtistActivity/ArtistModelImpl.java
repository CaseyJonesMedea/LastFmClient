package by.viachaslau.kukhto.lastfmclient.UI.ArtistActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.LibraryFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import rx.Observable;


/**
 * Created by kuhto on 27.03.2017.
 */

public class ArtistModelImpl extends ModelImpl implements ArtistsModel {

    public ArtistModelImpl() {
        super();
    }

    @Override
    public Observable getArtistInfo(String nameArtist) {
        Observable<Artist> observable = Observable.fromCallable(() -> {
            Artist artist = Artist.getInfo(nameArtist, Data.API_KEY);
            return artist;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getLibraryFragmentInformation(String artist) {
        Observable<LibraryFragmentInformation> observable = Observable.fromCallable(() -> {
            Collection<Artist> similarArtistCollection = Artist.getSimilar(artist, Data.API_KEY);
            List<Artist> similarArtist = new ArrayList<>(similarArtistCollection);
            Collection<Album> artistAlbumsCollection = Artist.getTopAlbums(artist, Data.API_KEY);
            List<Album> artistAlbums = new ArrayList<>(artistAlbumsCollection);
            Collection<Track> artistTopTracksCollection = Artist.getTopTracks(artist, Data.API_KEY);
            List<Track> artistTopTracks = new ArrayList<>(artistTopTracksCollection);
            return new LibraryFragmentInformation(similarArtist, artistAlbums, artistTopTracks);
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }
}
