package by.viachaslau.kukhto.llikelastfm.DI;



import by.viachaslau.kukhto.llikelastfm.UI.Album.AlbumActivity;
import by.viachaslau.kukhto.llikelastfm.UI.Artist.ArtistActivity;
import by.viachaslau.kukhto.llikelastfm.UI.List.ListActivity;
import by.viachaslau.kukhto.llikelastfm.UI.Search.SearchActivity;
import by.viachaslau.kukhto.llikelastfm.UI.Track.TrackActivity;
import by.viachaslau.kukhto.llikelastfm.UI.User.UserActivity;
import by.viachaslau.kukhto.llikelastfm.UI.Welcome.WelcomeActivity;
import by.viachaslau.kukhto.llikelastfm.UI.WorldChard.WorldChartActivity;
import by.viachaslau.kukhto.llikelastfm.UI.YouTube.YouTubeActivity;
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

