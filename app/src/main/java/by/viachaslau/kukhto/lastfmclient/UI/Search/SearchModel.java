package by.viachaslau.kukhto.lastfmclient.UI.Search;

import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public interface SearchModel {

    Observable getSearchArtist(String name);

    Observable getSearchAlbum(String name);

    Observable getSearchTrack(String name);
}
