package by.viachaslau.kukhto.lastfmclient.Activities.WorldChardActivity;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Activities.WorldChardActivity.WorldChartActivityFragments.WorldChartArtistsFragment;
import by.viachaslau.kukhto.lastfmclient.Activities.WorldChardActivity.WorldChartActivityFragments.WorldChartTracksFragment;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;

/**
 * Created by kuhto on 07.03.2017.
 */

public class WorldChartActivityPresenter implements WorldChartActivityIPresenter {


    private Context context;
    private WorldChartActivityIView iView;
    private Subscription subscription = Subscribers.empty();

    private Map<String, Fragment> fragments = new HashMap<>();
    private String fragmentInActivity;


    public WorldChartActivityPresenter(Context context, WorldChartActivityIView iView) {
        this.context = context;
        this.iView = iView;
        initializeArtistChart();
    }

    private void initializeArtistChart() {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getChartTopArtists().observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Artist>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(List<Artist> list) {
                iView.hideLoadProgressBar();
                WorldChartArtistsFragment artistsFragment = WorldChartArtistsFragment.newInstance((ArrayList<Artist>) list);
                fragments.put(WorldChartArtistsFragment.TAG, artistsFragment);
                iView.showFragment(artistsFragment, false, WorldChartArtistsFragment.TAG);
            }
        });
    }

    private void initialozeTracksChart() {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getChartTopTracks().observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Track>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(List<Track> list) {
                iView.hideLoadProgressBar();
                WorldChartTracksFragment tracksFragment = WorldChartTracksFragment.newInstance((ArrayList<Track>) list);
                fragments.put(WorldChartTracksFragment.TAG, tracksFragment);
                iView.showFragment(tracksFragment, false, WorldChartTracksFragment.TAG);
            }
        });
    }


    @Override
    public void onBtnUpdateClick() {
        if (fragmentInActivity.equals(WorldChartArtistsFragment.TAG)) {
            initializeArtistChart();
        } else if (fragmentInActivity.equals(WorldChartTracksFragment.TAG)) {
            initialozeTracksChart();
        }
    }

    @Override
    public void onBtnChartArtistClick() {
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
        context = null;
        iView = null;
        fragmentInActivity = null;
        fragments = null;
        RxUtils.unsubscribe(subscription);
    }
}
