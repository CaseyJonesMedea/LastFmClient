package by.viachaslau.kukhto.lastfmclient.UI.ListActivity;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.UI.ListActivity.ListActivityFragments.FullFragmentList;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.Subscribers;

/**
 * Created by VKukh on 04.03.2017.
 */

public class ListActivityPresenter implements ListActivityIPresenter {

    public static final String TAG = ListActivityPresenter.class.getSimpleName();

    private Subscription subscription = Subscribers.empty();

    private ListActivityIView iView;

    private FullFragmentList fullFragmentList;

    private String type;
    private String name;

    protected ListModelImpl model;


    public ListActivityPresenter(ListModelImpl model) {
        AppLog.log(TAG, "createListActivityPresenter");
        this.model = model;
    }


    @Override
    public void onCreate(ListActivityIView iView, Intent intent) {
        AppLog.log(TAG, "onCreate");
        this.iView = iView;
        fullFragmentList = new FullFragmentList();
        initList(intent);
    }

    private void initList(Intent intent) {
        AppLog.log(TAG, "initList");
        type = intent.getStringExtra(ListActivity.TITLE);
        name = intent.getStringExtra(ListActivity.NAME_USER);
        switch (type) {
            case Data.RECENT_TRACKS:
                initRecentTracks(name);
                break;
            case Data.TOP_ARTISTS:
                initTopArtists(name);
                break;
            case Data.TOP_ALBUMS:
                initTopAlbums(name);
                break;
            case Data.TOP_TRACKS:
                initTopTracks(name);
                break;
            case Data.LOVED_TRACKS:
                initLovedTracks(name);
                break;
            case Data.SIMILAR_ARTISTS:
                initSimilarArtists(name);
                break;
            case Data.ARTIST_TOP_ALBUMS:
                initArtistTopAlbums(name);
                break;
            case Data.ARTIST_TOP_TRACKS:
                initArtistTopTracks(name);
                break;
        }

    }

    public void initRecentTracks(String name) {
        AppLog.log(TAG, "initRecentTracks");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getRecentTracks(name).subscribe(new Subscriber<List<Track>>() {
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
                fullFragmentList = FullFragmentList.newTrackInstance((ArrayList<Track>) list, Data.RECENT_TRACKS, FullFragmentList.TRACK_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initTopArtists(String name) {
        AppLog.log(TAG, "initTopArtists");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getTopArtists(name).subscribe(new Subscriber<List<Artist>>() {
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
                fullFragmentList = FullFragmentList.newArtistInstance((ArrayList<Artist>) list, Data.TOP_ARTISTS, FullFragmentList.ARTIST_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initTopAlbums(String name) {
        AppLog.log(TAG, "initTopAlbums");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getTopAlbums(name).subscribe(new Subscriber<List<Album>>() {
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
            public void onNext(List<Album> list) {
                AppLog.log(TAG, "onNext");
                iView.hideLoadProgressBar();
                fullFragmentList = FullFragmentList.newAlbumInstance((ArrayList<Album>) list, Data.TOP_ALBUMS, FullFragmentList.ALBUM_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initTopTracks(String name) {
        AppLog.log(TAG, "initTopTracks");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getTopTracks(name).subscribe(new Subscriber<List<Track>>() {
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
                fullFragmentList = FullFragmentList.newTrackInstance((ArrayList<Track>) list, Data.TOP_TRACKS, FullFragmentList.TRACK_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initLovedTracks(String name) {
        AppLog.log(TAG, "initLovedTracks");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getLovedTracks(name).subscribe(new Subscriber<List<Track>>() {
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
                fullFragmentList = FullFragmentList.newTrackInstance((ArrayList<Track>) list, Data.LOVED_TRACKS, FullFragmentList.TRACK_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initSimilarArtists(String name) {
        AppLog.log(TAG, "initSimilarArtists");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getSimilarArtists(name).subscribe(new Subscriber<List<Artist>>() {
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
                fullFragmentList = FullFragmentList.newArtistInstance((ArrayList<Artist>) list, Data.SIMILAR_ARTISTS, FullFragmentList.ARTIST_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initArtistTopAlbums(String name) {
        AppLog.log(TAG, "initArtistTopAlbums");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getArtistTopAlbums(name).subscribe(new Subscriber<List<Album>>() {
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
            public void onNext(List<Album> list) {
                AppLog.log(TAG, "onNext");
                iView.hideLoadProgressBar();
                fullFragmentList = FullFragmentList.newAlbumInstance((ArrayList<Album>) list, Data.ARTIST_TOP_ALBUMS, FullFragmentList.ALBUM_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initArtistTopTracks(String name) {
        AppLog.log(TAG, "initArtistTopTracks");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getArtistTopTracks(name).subscribe(new Subscriber<List<Track>>() {
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
                fullFragmentList = FullFragmentList.newTrackInstance((ArrayList<Track>) list, Data.ARTIST_TOP_TRACKS, FullFragmentList.TRACK_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }


    @Override
    public void onBtnUpdateClick() {
        AppLog.log(TAG, "onBtnUpdateClick");
        switch (type) {
            case Data.RECENT_TRACKS:
                initRecentTracks(name);
                break;
            case Data.TOP_ARTISTS:
                initTopArtists(name);
                break;
            case Data.TOP_ALBUMS:
                initTopAlbums(name);
                break;
            case Data.TOP_TRACKS:
                initTopTracks(name);
                break;
            case Data.LOVED_TRACKS:
                initLovedTracks(name);
                break;
            case Data.SIMILAR_ARTISTS:
                initSimilarArtists(name);
                break;
            case Data.ARTIST_TOP_ALBUMS:
                initArtistTopAlbums(name);
                break;
            case Data.ARTIST_TOP_TRACKS:
                initArtistTopTracks(name);
                break;
        }
    }

    @Override
    public void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        iView = null;
        fullFragmentList = null;
        RxUtils.unsubscribe(subscription);
    }
}
