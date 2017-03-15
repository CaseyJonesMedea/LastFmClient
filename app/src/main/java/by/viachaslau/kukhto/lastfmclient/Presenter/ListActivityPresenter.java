package by.viachaslau.kukhto.lastfmclient.Presenter;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.Adapters.FullListAlbumSection;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.Adapters.FullListArtistSection;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.Adapters.FullListTrackSection;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.Fragments.ListFragmentFull;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.ListActivity;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.ListActivityIView;
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

    private ListFragmentFull listFragmentFull;

    private String type;
    private String name;


    public ListActivityPresenter(Context context, ListActivityIView iView, Intent intent) {
        this.context = context;
        this.iView = iView;
        this.intent = intent;
        listFragmentFull = new ListFragmentFull();
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
                listFragmentFull = ListFragmentFull.newTrackInstance((ArrayList<Track>) list, Data.RECENT_TRACKS, ListFragmentFull.TRACK_TYPE);
                iView.showFragment(listFragmentFull, false, ListFragmentFull.TAG);
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
                listFragmentFull = ListFragmentFull.newArtistInstance((ArrayList<Artist>) list, Data.TOP_ARTISTS, ListFragmentFull.ARTIST_TYPE);
                iView.showFragment(listFragmentFull, false, ListFragmentFull.TAG);
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
                listFragmentFull = ListFragmentFull.newAlbumInstance((ArrayList<Album>) list, Data.TOP_ALBUMS, ListFragmentFull.ALBUM_TYPE);
                iView.showFragment(listFragmentFull, false, ListFragmentFull.TAG);
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
                listFragmentFull = ListFragmentFull.newTrackInstance((ArrayList<Track>) list, Data.TOP_TRACKS, ListFragmentFull.TRACK_TYPE);
                iView.showFragment(listFragmentFull, false, ListFragmentFull.TAG);
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
                listFragmentFull = ListFragmentFull.newTrackInstance((ArrayList<Track>) list, Data.LOVED_TRACKS, ListFragmentFull.TRACK_TYPE);
                iView.showFragment(listFragmentFull, false, ListFragmentFull.TAG);
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
                listFragmentFull = ListFragmentFull.newArtistInstance((ArrayList<Artist>) list, Data.SIMILAR_ARTISTS, ListFragmentFull.ARTIST_TYPE);
                iView.showFragment(listFragmentFull, false, ListFragmentFull.TAG);
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
                listFragmentFull = ListFragmentFull.newAlbumInstance((ArrayList<Album>) list, Data.ARTIST_TOP_ALBUMS, ListFragmentFull.ALBUM_TYPE);
                iView.showFragment(listFragmentFull, false, ListFragmentFull.TAG);
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
                listFragmentFull = ListFragmentFull.newTrackInstance((ArrayList<Track>) list, Data.ARTIST_TOP_TRACKS, ListFragmentFull.TRACK_TYPE);
                iView.showFragment(listFragmentFull, false, ListFragmentFull.TAG);
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
