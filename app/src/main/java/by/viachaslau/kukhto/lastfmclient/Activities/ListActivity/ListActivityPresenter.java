package by.viachaslau.kukhto.lastfmclient.Activities.ListActivity;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.Activities.ListActivity.ListActivityFragments.FullFragmentList;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;

/**
 * Created by VKukh on 04.03.2017.
 */

public class ListActivityPresenter implements ListActivityIPresenter {

    private Subscription subscription = Subscribers.empty();

    private Context context;
    private ListActivityIView iView;
    private Intent intent;

    private FullFragmentList fullFragmentList;

    private String type;
    private String name;


    public ListActivityPresenter(Context context, ListActivityIView iView, Intent intent) {
        this.context = context;
        this.iView = iView;
        this.intent = intent;
        fullFragmentList = new FullFragmentList();
        initList(intent);
    }

    private void initList(Intent intent) {
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
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getRecentTracks(name).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Track>>() {
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
                fullFragmentList = FullFragmentList.newTrackInstance((ArrayList<Track>) list, Data.RECENT_TRACKS, FullFragmentList.TRACK_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initTopArtists(String name) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getTopArtists(name).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Artist>>() {
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
                fullFragmentList = FullFragmentList.newArtistInstance((ArrayList<Artist>) list, Data.TOP_ARTISTS, FullFragmentList.ARTIST_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initTopAlbums(String name) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getTopAlbums(name).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Album>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(List<Album> list) {
                iView.hideLoadProgressBar();
                fullFragmentList = FullFragmentList.newAlbumInstance((ArrayList<Album>) list, Data.TOP_ALBUMS, FullFragmentList.ALBUM_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initTopTracks(String name) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getTopTracks(name).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Track>>() {
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
                fullFragmentList = FullFragmentList.newTrackInstance((ArrayList<Track>) list, Data.TOP_TRACKS, FullFragmentList.TRACK_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initLovedTracks(String name) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getLovedTracks(name).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Track>>() {
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
                fullFragmentList = FullFragmentList.newTrackInstance((ArrayList<Track>) list, Data.LOVED_TRACKS, FullFragmentList.TRACK_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initSimilarArtists(String name) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getSimilarArtists(name).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Artist>>() {
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
                fullFragmentList = FullFragmentList.newArtistInstance((ArrayList<Artist>) list, Data.SIMILAR_ARTISTS, FullFragmentList.ARTIST_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initArtistTopAlbums(String name) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getArtistTopAlbums(name).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Album>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(List<Album> list) {
                iView.hideLoadProgressBar();
                fullFragmentList = FullFragmentList.newAlbumInstance((ArrayList<Album>) list, Data.ARTIST_TOP_ALBUMS, FullFragmentList.ALBUM_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }

    public void initArtistTopTracks(String name) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getArtistTopTracks(name).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Track>>() {
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
                fullFragmentList = FullFragmentList.newTrackInstance((ArrayList<Track>) list, Data.ARTIST_TOP_TRACKS, FullFragmentList.TRACK_TYPE);
                iView.showFragment(fullFragmentList, false, FullFragmentList.TAG);
            }
        });
    }


    @Override
    public void onBtnUpdateClick() {
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
}
