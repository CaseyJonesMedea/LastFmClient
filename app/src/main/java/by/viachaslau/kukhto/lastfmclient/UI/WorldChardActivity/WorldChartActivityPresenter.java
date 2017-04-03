package by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity.WorldChartActivityFragments.WorldChartArtistsFragment;
import by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity.WorldChartActivityFragments.WorldChartTracksFragment;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.Subscribers;

/**
 * Created by kuhto on 07.03.2017.
 */

public class WorldChartActivityPresenter implements WorldChartActivityIPresenter {

    public static final String TAG = WorldChartActivityPresenter.class.getSimpleName();


    private WorldChartActivityIView iView;
    private Subscription subscription = Subscribers.empty();

    private Map<String, Fragment> fragments = new HashMap<>();
    private String fragmentInActivity;

    protected WorldChartModelImpl model;


    public WorldChartActivityPresenter(WorldChartModelImpl model) {
        AppLog.log(TAG, "createWorldChartActivityPresenter");
        this.model = model;
    }

    @Override
    public void onCreate(WorldChartActivityIView iView){
        AppLog.log(TAG, "onCreate");
        this.iView = iView;
        initializeArtistChart();
    }


    private void initializeArtistChart() {
        AppLog.log(TAG, "initializeArtistChart");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getChartTopArtists().subscribe(new Subscriber<List<Artist>>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(List<Artist> list) {
                AppLog.log(TAG, "onNext");
                iView.hideLoadProgressBar();
                fragmentInActivity = WorldChartArtistsFragment.TAG;
                WorldChartArtistsFragment artistsFragment = WorldChartArtistsFragment.newInstance((ArrayList<Artist>) list);
                fragments.put(WorldChartArtistsFragment.TAG, artistsFragment);
                iView.showFragment(artistsFragment, false, WorldChartArtistsFragment.TAG);
            }
        });
    }

    private void initialozeTracksChart() {
        AppLog.log(TAG, "initialozeTracksChart");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getChartTopTracks().subscribe(new Subscriber<List<Track>>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(List<Track> list) {
                AppLog.log(TAG, "onNext");
                iView.hideLoadProgressBar();
                fragmentInActivity = WorldChartTracksFragment.TAG;
                WorldChartTracksFragment tracksFragment = WorldChartTracksFragment.newInstance((ArrayList<Track>) list);
                fragments.put(WorldChartTracksFragment.TAG, tracksFragment);
                iView.showFragment(tracksFragment, false, WorldChartTracksFragment.TAG);
            }
        });
    }


    @Override
    public void onBtnUpdateClick() {
        AppLog.log(TAG, "onBtnUpdateClick");
        if (fragmentInActivity.equals(WorldChartArtistsFragment.TAG)) {
            initializeArtistChart();
        } else if (fragmentInActivity.equals(WorldChartTracksFragment.TAG)) {
            initialozeTracksChart();
        }
    }

    @Override
    public void onBtnChartArtistClick() {
        AppLog.log(TAG, "onBtnChartArtistClick");
        fragmentInActivity = WorldChartArtistsFragment.TAG;
        WorldChartArtistsFragment fragmentUser = null;
        for (Map.Entry entry : fragments.entrySet()) {
            if (entry.getKey().equals(WorldChartArtistsFragment.TAG)) {
                fragmentUser = (WorldChartArtistsFragment) entry.getValue();
                break;
            }
        }
        if (fragmentUser != null) {
            fragments.put(WorldChartArtistsFragment.TAG, fragmentUser);
            iView.showFragment(fragmentUser, false, WorldChartArtistsFragment.TAG);
        } else {
            initializeArtistChart();
        }
    }

    @Override
    public void onBtnChartTracksClick() {
        AppLog.log(TAG, "onBtnChartTracksClick");
        fragmentInActivity = WorldChartTracksFragment.TAG;
        WorldChartTracksFragment fragmentUser = null;
        for (Map.Entry entry : fragments.entrySet()) {
            if (entry.getKey().equals(WorldChartTracksFragment.TAG)) {
                fragmentUser = (WorldChartTracksFragment) entry.getValue();
                break;
            }
        }
        if (fragmentUser != null) {
            fragments.put(WorldChartTracksFragment.TAG, fragmentUser);
            iView.showFragment(fragmentUser, false, WorldChartTracksFragment.TAG);
        } else {
            initialozeTracksChart();
        }
    }

    @Override
    public void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        iView = null;
        fragmentInActivity = null;
        fragments = null;
        RxUtils.unsubscribe(subscription);
    }
}
