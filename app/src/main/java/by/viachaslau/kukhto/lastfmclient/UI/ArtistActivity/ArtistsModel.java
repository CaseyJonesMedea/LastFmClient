package by.viachaslau.kukhto.lastfmclient.UI.ArtistActivity;

import rx.Observable;

/**
 * Created by kuhto on 27.03.2017.
 */

public interface ArtistsModel {

    Observable getArtistInfo(String nameArtist);

    Observable getLibraryFragmentInformation(String artist);

}
