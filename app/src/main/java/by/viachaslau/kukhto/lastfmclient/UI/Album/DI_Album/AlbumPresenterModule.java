package by.viachaslau.kukhto.lastfmclient.UI.Album.DI_Album;

import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumActivityPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kuhto on 29.03.2017.
 */

@Module
public class AlbumPresenterModule {

    @Provides
    AlbumActivityPresenter provideAlbumActivityPresenter(){
        return new AlbumActivityPresenter();
    }

}
