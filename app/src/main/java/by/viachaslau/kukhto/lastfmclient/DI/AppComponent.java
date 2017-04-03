package by.viachaslau.kukhto.lastfmclient.DI;



import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumActivity;
import by.viachaslau.kukhto.lastfmclient.UI.Artist.ArtistActivity;
import by.viachaslau.kukhto.lastfmclient.UI.List.ListActivity;
import by.viachaslau.kukhto.lastfmclient.UI.Search.SearchActivity;
import by.viachaslau.kukhto.lastfmclient.UI.Track.TrackActivity;
import by.viachaslau.kukhto.lastfmclient.UI.User.UserActivity;
import by.viachaslau.kukhto.lastfmclient.UI.Welcome.WelcomeActivity;
import by.viachaslau.kukhto.lastfmclient.UI.WorldChard.WorldChartActivity;
import by.viachaslau.kukhto.lastfmclient.UI.YouTube.YouTubeActivity;
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

