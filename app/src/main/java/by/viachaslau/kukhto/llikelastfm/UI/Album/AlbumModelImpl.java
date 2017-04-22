package by.viachaslau.kukhto.llikelastfm.UI.Album;


import by.viachaslau.kukhto.llikelastfm.Others.Data;
import by.viachaslau.kukhto.llikelastfm.Others.Model.ModelImpl;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Album;
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
