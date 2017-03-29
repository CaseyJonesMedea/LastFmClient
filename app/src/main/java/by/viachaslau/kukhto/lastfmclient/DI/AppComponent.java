package by.viachaslau.kukhto.lastfmclient.DI;



import by.viachaslau.kukhto.lastfmclient.UI.Album.DI_Album.ActivityComponent;
import by.viachaslau.kukhto.lastfmclient.UI.Album.DI_Album.AlbumModelModule;
import by.viachaslau.kukhto.lastfmclient.UI.Album.DI_Album.AlbumPresenterModule;
import dagger.Component;

/**
 * Created by kuhto on 28.03.2017.
 */

@Component(modules = {AlbumModelModule.class, AlbumPresenterModule.class})
public interface AppComponent {

    ActivityComponent createActivityComponent();

}

