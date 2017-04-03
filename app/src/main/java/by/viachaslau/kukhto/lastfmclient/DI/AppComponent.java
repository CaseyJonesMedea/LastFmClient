package by.viachaslau.kukhto.lastfmclient.DI;



import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumActivity;
import by.viachaslau.kukhto.lastfmclient.UI.Artist.ArtistActivity;
import by.viachaslau.kukhto.lastfmclient.UI.ListActivity.ListActivity;
import by.viachaslau.kukhto.lastfmclient.UI.SearchActivity.SearchActivity;
import by.viachaslau.kukhto.lastfmclient.UI.TrackActivity.TrackActivity;
import by.viachaslau.kukhto.lastfmclient.UI.UserActivity.UserActivity;
import by.viachaslau.kukhto.lastfmclient.UI.WelcomeActivity.WelcomeActivity;
import by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity.WorldChartActivity;
import by.viachaslau.kukhto.lastfmclient.UI.YouTubeActivity.YouTubeActivity;
import dagger.Component;

/**
 * Created by kuhto on 28.03.2017.
 */

@Component(modules = {PresenterModule.class, ImageLoaderInstanceModule.class})
public interface AppComponent {

    void inject(AlbumActivity activity);

    void inject(ArtistActivity activity);

    void inject(ListActivity activity);

    void inject(SearchActivity activity);

    void inject(TrackActivity activity);

    void inject(UserActivity activity);

    void inject(WelcomeActivity activity);

    void inject(WorldChartActivity activity);

    void inject(YouTubeActivity activity);
}

