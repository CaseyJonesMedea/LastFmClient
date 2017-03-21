package by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

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

    private Context context;
    private ArtistActivityIView iView;
    private Subscription subscription = Subscribers.empty();
    private Artist artist;

    private Map<String, Fragment> fragments = new HashMap<>();
    private String fragmentInActivity;


    public ArtistActivityPresenter(Context context, ArtistActivityIView iView, Intent intent) {
        this.context = context;
        this.iView = iView;
        initializeArtistInformation(intent);
    }


    private void initializeArtistInformation(Intent intent) {
        String artistName = intent.getStringExtra(ArtistActivity.ARTIST);

        loadArtist(artistName);
    }

    private void loadArtist(String artistName) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getArtistInfo(artistName).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Artist>() {
            @Override
            public void onCompleted() {
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
            }

            @Override
            public void onNext(Artist artist) {
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
        fragmentInActivity = InfoFragmentArtist.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getArtistInfo(artist.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Artist>() {
            @Override
            public void onCompleted() {
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
            }

            @Override
            public void onNext(Artist artist) {
                InfoFragmentArtist infoFragmentArtist = InfoFragmentArtist.newInstance(artist);
                fragments.put(InfoFragmentArtist.TAG, infoFragmentArtist);
                iView.showFragment(infoFragmentArtist, false, InfoFragmentArtist.TAG);
            }
        });
    }

    private void initLibraryFragment() {
        fragmentInActivity = LibraryFragmentArtist.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getLibraryFragmentInformation(artist.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LibraryFragmentInformation>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.hideLoadProgressBar();
                iView.showErrorFragment();
            }

            @Override
            public void onNext(LibraryFragmentInformation libraryFragmentInformation) {
                iView.hideLoadProgressBar();
                LibraryFragmentArtist libraryFragmentArtist = LibraryFragmentArtist.newInstance(libraryFragmentInformation, artist.getName());
                fragments.put(LibraryFragmentArtist.TAG, libraryFragmentArtist);
                iView.showFragment(libraryFragmentArtist, false, InfoFragmentArtist.TAG);
            }
        });
    }


    @Override
    public void onBtnInfoClick() {
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
        if (fragmentInActivity.equals(LibraryFragmentArtist.TAG)) {
            initLibraryFragment();
        } else if (fragmentInActivity.equals(InfoFragmentArtist.TAG)) {
            initInfoFragment();
        }
    }

    @Override
    public void onBtnShareClick() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, artist.getUrl());
        context.startActivity(Intent.createChooser(share, "Share link!"));
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
