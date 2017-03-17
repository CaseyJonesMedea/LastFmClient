package by.viachaslau.kukhto.lastfmclient.Activities.AlbumActivity;

import android.content.Context;
import android.content.Intent;

import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
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

    private Context context;
    private AlbumActivityIView iView;
    private Subscription subscription = Subscribers.empty();
    private Album album;

    private String artistName;
    private String albumName;


    public AlbumActivityPresenter(Context context, AlbumActivityIView iView, Intent intent) {
        this.context = context;
        this.iView = iView;
        initializeAlbumInformation(intent);
    }


    private void initializeAlbumInformation(Intent intent) {
        artistName = intent.getStringExtra(AlbumActivity.ARTIST_NAME);
        albumName = intent.getStringExtra(AlbumActivity.ALBUM_NAME);
        loadAlbum(artistName, albumName);
    }

    private void loadAlbum(String artistName, String albumName) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getAlbumInfo(artistName, albumName).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Album>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(Album album) {
                initAlbum(album);
                iView.hideLoadProgressBar();
                iView.initAlbumFull(album);
                initFullAlbumFragment(album);
            }
        });
    }

    private void initAlbum(Album album) {
        this.album = album;
    }


    private void initFullAlbumFragment(Album album) {
        FullFragmentAlbum fullFragmentAlbum = FullFragmentAlbum.newInstance(album);
        iView.showFragment(fullFragmentAlbum, false, FullFragmentAlbum.TAG);
    }


    @Override
    public void onBtnUpdateClick() {
        loadAlbum(artistName, albumName);
    }
}
