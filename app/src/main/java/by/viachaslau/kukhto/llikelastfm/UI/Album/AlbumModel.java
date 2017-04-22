package by.viachaslau.kukhto.llikelastfm.UI.Album;

import rx.Observable;

/**
 * Created by kuhto on 27.03.2017.
 */

public interface AlbumModel {

    Observable getAlbumInfo(String nameArtist, String nameAlbum);

}
