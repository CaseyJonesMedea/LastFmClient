package by.viachaslau.kukhto.lastfmclient.UI.SearchActivity;

import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public interface SearchModel {

    Observable getSearchArtist(String name);

    Observable getSearchAlbum(String name);

    Observable getSearchTrack(String name);
}
