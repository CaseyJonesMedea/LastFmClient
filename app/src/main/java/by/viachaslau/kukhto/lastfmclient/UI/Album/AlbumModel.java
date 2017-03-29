package by.viachaslau.kukhto.lastfmclient.UI.Album;

import rx.Observable;

/**
 * Created by kuhto on 27.03.2017.
 */

public interface AlbumModel {

    Observable getAlbumInfo(String nameArtist, String nameAlbum);

}
