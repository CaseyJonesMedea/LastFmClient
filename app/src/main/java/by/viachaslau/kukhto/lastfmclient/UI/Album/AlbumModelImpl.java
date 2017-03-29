package by.viachaslau.kukhto.lastfmclient.UI.Album;


import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import rx.Observable;


/**
 * Created by kuhto on 27.03.2017.
 */

public class AlbumModelImpl extends ModelImpl implements AlbumModel {


    public AlbumModelImpl() {
        super();
    }

    @Override
    public Observable getAlbumInfo(String nameArtist, String nameAlbum) {
        Observable<Album> observable = Observable.fromCallable(() -> {
            Album album = Album.getInfo(nameArtist, nameAlbum, Data.API_KEY);
            return album;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }
}
