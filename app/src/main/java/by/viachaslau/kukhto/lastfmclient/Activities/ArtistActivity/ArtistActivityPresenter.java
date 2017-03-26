package by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity;


import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.LibraryFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity.ArtistActivityFragments.InfoFragmentArtist;
import by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity.ArtistActivityFragments.LibraryFragmentArtist;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;

/**
 * Created by kuhto on 03.03.2017.
 */

public class ArtistActivityPresenter implements ArtistActivityIPresenter {

    public static final String TAG = ArtistActivityPresenter.class.getSimpleName();

    private ArtistActivityIView iView;
    private Subscription subscription = Subscribers.empty();
    private Artist artist;

    private String artistName;

    private Map<String, Fragment> fragments = new HashMap<>();
    private String fragmentInActivity;


    public ArtistActivityPresenter(ArtistActivityIView iView, Intent intent) {
        AppLog.log(TAG, "createArtistActivityPresenter");
        this.iView = iView;
        initializeArtistInformation(intent);
    }


    private void initializeArtistInformation(Intent intent) {
        AppLog.log(TAG, "initializeArtistInformation");
        artistName = intent.getStringExtra(ArtistActivity.ARTIST);
        loadArtist(artistName);
    }

    private void loadArtist(String artistName) {
        AppLog.log(TAG, "loadArtist");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getArtistInfo(artistName).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Artist>() {
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
            public void onNext(Artist artist) {
                AppLog.log(TAG, "onNext");
                initArtist(artist);
                iView.initArtistFull(artist);
                initInfoFragment();
            }
        });
    }

    private void initArtist(Artist artist) {
        this.artist = artist;
    }

    private void initInfoFragment() {
        AppLog.log(TAG, "initInfoFragment");
        fragmentInActivity = InfoFragmentArtist.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getArtistInfo(artist.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Artist>() {
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
            public void onNext(Artist artist) {
                AppLog.log(TAG, "onNext");
                InfoFragmentArtist infoFragmentArtist = InfoFragmentArtist.newInstance(artist);
                fragments.put(InfoFragmentArtist.TAG, infoFragmentArtist);
                iView.showFragment(infoFragmentArtist, false, InfoFragmentArtist.TAG);
            }
        });
    }

    private void initLibraryFragment() {
        AppLog.log(TAG, "initLibraryFragment");
        fragmentInActivity = LibraryFragmentArtist.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getLibraryFragmentInformation(artist.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LibraryFragmentInformation>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.hideLoadProgressBar();
                iView.showErrorFragment();
            }

            @Override
            public void onNext(LibraryFragmentInformation libraryFragmentInformation) {
                AppLog.log(TAG, "onNext");
                iView.hideLoadProgressBar();
                LibraryFragmentArtist libraryFragmentArtist = LibraryFragmentArtist.newInstance(libraryFragmentInformation, artist.getName());
                fragments.put(LibraryFragmentArtist.TAG, libraryFragmentArtist);
                iView.showFragment(libraryFragmentArtist, false, InfoFragmentArtist.TAG);
            }
        });
    }


    @Override
    public void onBtnInfoClick() {
        AppLog.log(TAG, "onBtnInfoClick");
        fragmentInActivity = InfoFragmentArtist.TAG;
        InfoFragmentArtist fragmentUser = null;
        for (Map.Entry entry : fragments.entrySet()) {
            if (entry.getKey().equals(InfoFragmentArtist.TAG)) {
                fragmentUser = (InfoFragmentArtist) entry.getValue();
                break;
            }
        }
        if (fragmentUser != null) {
            fragments.put(InfoFragmentArtist.TAG, fragmentUser);
            iView.showFragment(fragmentUser, false, InfoFragmentArtist.TAG);
        } else {
            initInfoFragment();
        }
    }

    @Override
    public void onBtnLibraryClick() {
        AppLog.log(TAG, "onBtnLibraryClick");
        fragmentInActivity = LibraryFragmentArtist.TAG;
        LibraryFragmentArtist fragmentUser = null;
        for (Map.Entry entry : fragments.entrySet()) {
            if (entry.getKey().equals(LibraryFragmentArtist.TAG)) {
                fragmentUser = (LibraryFragmentArtist) entry.getValue();
                break;
            }
        }
        if (fragmentUser != null) {
            fragments.put(LibraryFragmentArtist.TAG, fragmentUser);
            iView.showFragment(fragmentUser, false, LibraryFragmentArtist.TAG);
        } else {
            initLibraryFragment();
        }
    }

    @Override
    public void onBtnUpdateClick() {
        AppLog.log(TAG, "onBtnUpdateClick");
        if (artist == null) {
            loadArtist(artistName);
        } else {
            if (fragmentInActivity.equals(LibraryFragmentArtist.TAG)) {
                initLibraryFragment();
            } else if (fragmentInActivity.equals(InfoFragmentArtist.TAG)) {
                initInfoFragment();
            }
        }
    }

    @Override
    public void onBtnShareClick() {
        AppLog.log(TAG, "onBtnShareClick");
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, artist.getUrl());
        iView.getContext().startActivity(Intent.createChooser(share, "Share link!"));
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
