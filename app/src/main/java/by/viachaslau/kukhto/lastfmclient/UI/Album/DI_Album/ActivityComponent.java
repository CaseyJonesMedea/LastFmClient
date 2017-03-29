package by.viachaslau.kukhto.lastfmclient.UI.Album.DI_Album;

import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumActivity;
import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumActivityPresenter;
import dagger.Subcomponent;

/**
 * Created by kuhto on 29.03.2017.
 */


@Subcomponent(modules = {AlbumModelModule.class, AlbumPresenterModule.class})
public interface ActivityComponent {

    void inject(AlbumActivity activity);

    void inject(AlbumActivityPresenter presenter);
}
