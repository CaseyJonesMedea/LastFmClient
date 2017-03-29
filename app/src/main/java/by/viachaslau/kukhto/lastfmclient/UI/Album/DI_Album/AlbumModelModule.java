package by.viachaslau.kukhto.lastfmclient.UI.Album.DI_Album;


import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumModelImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kuhto on 28.03.2017.
 */

@Module
public class AlbumModelModule {

    @Provides
    AlbumModelImpl provideAlbumModel(){
        return new AlbumModelImpl();
    }
}
