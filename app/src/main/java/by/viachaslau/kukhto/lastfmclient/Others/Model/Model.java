package by.viachaslau.kukhto.lastfmclient.Others.Model;


import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.UserInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import rx.Observable;

/**
 * Created by CaseyJones on 11.12.2016.
 */

public interface Model {

    Observable getHomeFragmentInformation(String user);

    Observable getChartFragmentInformation(String user);

    Observable getFriendsFragmentInformation(String user);

    Observable getMobileSession(UserInformation information);

    Observable getUserInfo(String nameUser);

    Observable getArtistInfo(String nameArtist);

    Observable getAlbumInfo(String nameArtist, String nameAlbum);

    Observable getLibraryFragmentInformation(String artist);

    Observable getSharedPreferencesUserInfo();

    Observable getSearchArtist(String name);

    Observable getSearchAlbum(String name);

    Observable getSearchTrack(String name);

    Observable getRecentTracks(String name);

    Observable getTopArtists(String name);

    Observable getTopAlbums(String name);

    Observable getTopTracks(String name);

    Observable getLovedTracks(String name);

    Observable getLovedTracksJson(String name);

    Observable getSimilarArtists(String name);

    Observable getArtistTopAlbums(String name);

    Observable getArtistTopTracks(String name);

    Observable getChartTopArtists();

    Observable getChartTopTracks();

    Observable getYouTubeUrl(String urlTrack);

    Observable getResultLoveTrack(Track track);

    Observable getResultUnLoveTrack(Track track);

}
