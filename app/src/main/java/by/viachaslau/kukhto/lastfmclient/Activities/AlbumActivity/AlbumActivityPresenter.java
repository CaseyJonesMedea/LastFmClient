package by.viachaslau.kukhto.lastfmclient.Activities.AlbumActivity;

import android.content.Intent;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Activities.AlbumActivity.AlbumActivityFragments.FullFragmentAlbum;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;

/**
 * Created by VKukh on 04.03.2017.
 */

public class AlbumActivityPresenter implements AlbumActivityIPresenter {

    public static final String TAG = AlbumActivityPresenter.class.getSimpleName();

    private AlbumActivityIView iView;
    private Subscription subscription = Subscribers.empty();
    private Album album;

    private String artistName;
    private String albumName;


    public AlbumActivityPresenter(AlbumActivityIView iView, Intent intent) {
        this.iView = iView;
        AppLog.log(TAG, "CreateAlbumActivityPresenter");
        initializeAlbumInformation(intent);
    }


    private void initializeAlbumInformation(Intent intent) {
        AppLog.log(TAG, "initializeAlbumInformation");
        artistName = intent.getStringExtra(AlbumActivity.ARTIST_NAME);
        albumName = intent.getStringExtra(AlbumActivity.ALBUM_NAME);
        loadAlbum(artistName, albumName);
    }

    private void loadAlbum(String artistName, String albumName) {
        AppLog.log(TAG, "loadAlbum");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getAlbumInfo(artistName, albumName).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Album>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(Album album) {
                AppLog.log(TAG, "onNext");
                initAlbum(album);
                iView.initAlbumFull(album);
                initFullAlbumFragment(album);
            }
        });
    }

    private void initAlbum(Album album) {
        AppLog.log(TAG, "initAlbum");
        this.album = album;
    }


    private void initFullAlbumFragment(Album album) {
        FullFragmentAlbum fullFragmentAlbum = FullFragmentAlbum.newInstance(album);
        iView.showFragment(fullFragmentAlbum, false, FullFragmentAlbum.TAG);
    }


    @Override
    public void onBtnUpdateClick() {
        AppLog.log(TAG, "onBtnUpdateClick");
        loadAlbum(artistName, albumName);
    }

    @Override
    public void onBtnShareClick() {
        AppLog.log(TAG, "onBtnUpdateClick");
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, album.getUrl());
        iView.getContext().startActivity(Intent.createChooser(share, "Share link!"));
    }

    @Override
    public void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        iView = null;
        album = null;
        RxUtils.unsubscribe(subscription);
    }
}
