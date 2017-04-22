package by.viachaslau.kukhto.llikelastfm.UI.List;

import rx.Observable;

/**
 * Created by kuhto on 27.03.2017.
 */

public interface ListModel {

    Observable getRecentTracks(String name);

    Observable getTopArtists(String name);

    Observable getTopAlbums(String name);

    Observable getTopTracks(String name);

    Observable getLovedTracks(String name);

    Observable getSimilarArtists(String name);

    Observable getArtistTopAlbums(String name);

    Observable getArtistTopTracks(String name);


}
