package by.viachaslau.kukhto.llikelastfm.Others.Model.modelApp;

import java.io.Serializable;
import java.util.List;

import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Album;

/**
 * Created by CaseyJones on 28.01.2017.
 */

public class HomeFragmentInformation implements Serializable{

    private List<Track> recentTracks;
    private List<Artist> topArtists;
    private List<Album> topAlbums;
    private List<Track> topTracks;
    private List<Track> lovedTracks;


    public HomeFragmentInformation(List<Track> recentTracks, List<Artist> topArtists, List<Album> topAlbums, List<Track> topTracks, List<Track> lovedTracks) {
        this.recentTracks = recentTracks;
        this.topArtists = topArtists;
        this.topAlbums = topAlbums;
        this.topTracks = topTracks;
        this.lovedTracks = lovedTracks;
    }

    public List<Track> getRecentTracks() {
        return recentTracks;
    }

    public void setRecentTracks(List<Track> recentTracks) {
        this.recentTracks = recentTracks;
    }

    public List<Artist> getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(List<Artist> topArtists) {
        this.topArtists = topArtists;
    }

    public List<Album> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(List<Album> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public List<Track> getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(List<Track> topTracks) {
        this.topTracks = topTracks;
    }

    public List<Track> getLovedTracks() {
        return lovedTracks;
    }

    public void setLovedTracks(List<Track> lovedTracks) {
        this.lovedTracks = lovedTracks;
    }

}
