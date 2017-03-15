package by.viachaslau.kukhto.lastfmclient.Model.modelApp;

import java.io.Serializable;
import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Track;

/**
 * Created by VKukh on 04.03.2017.
 */

public class LibraryFragmentInformation implements Serializable {

    private List<Artist> similarArtists;
    private List<Album> albumsArtist;
    private List<Track> tracksArtist;

    public LibraryFragmentInformation(List<Artist> similarArtists, List<Album> albumsArtist, List<Track> tracksArtist) {
        this.similarArtists = similarArtists;
        this.albumsArtist = albumsArtist;
        this.tracksArtist = tracksArtist;
    }

    public List<Artist> getSimilarArtists() {
        return similarArtists;
    }

    public void setSimilarArtists(List<Artist> similarArtists) {
        this.similarArtists = similarArtists;
    }

    public List<Album> getAlbumsArtist() {
        return albumsArtist;
    }

    public void setAlbumsArtist(List<Album> albumsArtist) {
        this.albumsArtist = albumsArtist;
    }

    public List<Track> getTracksArtist() {
        return tracksArtist;
    }

    public void setTracksArtist(List<Track> tracksArtist) {
        this.tracksArtist = tracksArtist;
    }
}
