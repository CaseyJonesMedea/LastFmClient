package by.viachaslau.kukhto.lastfmclient.DI;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumModelImpl;
import by.viachaslau.kukhto.lastfmclient.UI.Artist.ArtistActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.UI.Artist.ArtistModelImpl;
import by.viachaslau.kukhto.lastfmclient.UI.ListActivity.ListActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.UI.ListActivity.ListModelImpl;
import by.viachaslau.kukhto.lastfmclient.UI.SearchActivity.SearchActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.UI.SearchActivity.SearchModelImpl;
import by.viachaslau.kukhto.lastfmclient.UI.TrackActivity.TrackActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.UI.TrackActivity.TrackModelImpl;
import by.viachaslau.kukhto.lastfmclient.UI.UserActivity.UserActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.UI.UserActivity.UserModelImpl;
import by.viachaslau.kukhto.lastfmclient.UI.WelcomeActivity.WelcomeModelImpl;
import by.viachaslau.kukhto.lastfmclient.UI.WelcomeActivity.WelcomePresenter;
import by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity.WorldChartActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity.WorldChartModelImpl;
import by.viachaslau.kukhto.lastfmclient.UI.YouTubeActivity.YouTubeActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.UI.YouTubeActivity.YouTubeModelImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kuhto on 29.03.2017.
 */

@Module
public class PresenterModule {

    public static final String TAG = PresenterModule.class.getSimpleName();

    @Provides
    AlbumActivityPresenter provideAlbumActivityPresenter(AlbumModelImpl model) {
        AppLog.log(TAG, "provideAlbumActivityPresenter");
        return new AlbumActivityPresenter(model);
    }

    @Provides
    AlbumModelImpl provideAlbumModelImpl() {
        AppLog.log(TAG, "provideAlbumModelImpl");
        return new AlbumModelImpl();
    }

    @Provides
    ArtistActivityPresenter provideArtistActivityPresenter(ArtistModelImpl model){
        AppLog.log(TAG, "provideArtistActivityPresenter");
        return new ArtistActivityPresenter(model);
    }

    @Provides
    ArtistModelImpl provideArtistModelImpl(){
        AppLog.log(TAG, "provideArtistModelImpl");
        return new ArtistModelImpl();
    }

    @Provides
    ListActivityPresenter provideListActivityPresenter(ListModelImpl model){
        AppLog.log(TAG, "provideListActivityPresenter");
        return new ListActivityPresenter(model);
    }

    @Provides
    ListModelImpl provideListModelImpl(){
        AppLog.log(TAG, "provideListModelImpl");
        return new ListModelImpl();
    }

    @Provides
    SearchActivityPresenter provideSearchActivityPresenter(SearchModelImpl model){
        AppLog.log(TAG, "provideSearchActivityPresenter");
        return new SearchActivityPresenter(model);
    }

    @Provides
    SearchModelImpl provideSearchModelImpl(){
        AppLog.log(TAG, "provideSearchModelImpl");
        return new SearchModelImpl();
    }

    @Provides
    TrackActivityPresenter provideTrackActivityPresenter(TrackModelImpl model){
        AppLog.log(TAG, "provideTrackActivityPresenter");
        return new TrackActivityPresenter(model);
    }

    @Provides
    TrackModelImpl provideTrackModelImpl(){
        AppLog.log(TAG, "provideTrackModelImpl");
        return new TrackModelImpl();
    }

    @Provides
    UserActivityPresenter provideUserActivityPresenter(UserModelImpl model){
        AppLog.log(TAG, "provideUserActivityPresenter");
        return new UserActivityPresenter(model);
    }

    @Provides
    UserModelImpl provideUserModelImpl(){
        AppLog.log(TAG, "provideUserModelImpl");
        return new UserModelImpl();
    }

    @Provides
    WelcomePresenter provideWelcomePresenter(WelcomeModelImpl model){
        AppLog.log(TAG, "provideWelcomePresenter");
        return new WelcomePresenter(model);
    }

    @Provides
    WelcomeModelImpl provideWelcomeModelImpl(){
        AppLog.log(TAG, "provideWelcomeModelImpl");
        return new WelcomeModelImpl();
    }

    @Provides
    WorldChartActivityPresenter provideWorldChartPresenter(WorldChartModelImpl model){
        AppLog.log(TAG, "provideWorldChartPresenter");
        return new WorldChartActivityPresenter(model);
    }

    @Provides
    WorldChartModelImpl provideWorldChartModel(){
        AppLog.log(TAG, "provideWorldChartModel");
        return new WorldChartModelImpl();
    }

    @Provides
    YouTubeActivityPresenter provideYouTubeActivityProvide(YouTubeModelImpl model){
        AppLog.log(TAG, "provideYouTubeActivityProvide");
        return new YouTubeActivityPresenter(model);
    }

    @Provides
    YouTubeModelImpl provideYouTubeModelImpl(){
        AppLog.log(TAG, "provideYouTubeModelImpl");
        return new YouTubeModelImpl();
    }


}
