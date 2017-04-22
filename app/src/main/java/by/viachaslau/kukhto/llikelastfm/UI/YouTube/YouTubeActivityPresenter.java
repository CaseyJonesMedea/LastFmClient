package by.viachaslau.kukhto.llikelastfm.UI.YouTube;

import android.content.Intent;

import java.util.List;


import by.viachaslau.kukhto.llikelastfm.Others.Model.AppLog;
import by.viachaslau.kukhto.llikelastfm.Others.Model.RxUtils;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Result;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.scrobble.ScrobbleResult;
import by.viachaslau.kukhto.llikelastfm.Others.SingletonSession;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.observers.Subscribers;

/**
 * Created by VKukh on 19.03.2017.
 */

public class YouTubeActivityPresenter implements YouTubeActivityIPresenter {

    public static final String TAG = YouTubeActivityPresenter.class.getSimpleName();

    private YouTubeActivityIView iView;

    private String urlYouTube;

    private boolean trackIsLove;

    private Track track;

    private Subscription subscription = Subscribers.empty();

    protected YouTubeModelImpl model;


    public YouTubeActivityPresenter(YouTubeModelImpl model) {
        AppLog.log(TAG, "createYouTubeActivityPresenter");
        this.model = model;
    }

    @Override
    public void onCreate(YouTubeActivityIView iView, String code, Track track) {
        AppLog.log(TAG, "onCreate");
        this.iView = iView;
        this.track = track;
        initialize(code);
    }

    private void initialize(String code) {
        AppLog.log(TAG, "initialize");
        urlYouTube = "https://www.youtube.com/watch?v=" + code;
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getLovedTracksJson(SingletonSession.getInstance().getSession().getUsername()).subscribe(new Subscriber<List<Track>>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
            }

            @Override
            public void onNext(List<Track> tracks) {
                AppLog.log(TAG, "onNext");
                if (equalsTracks(tracks, track)) {
                    iView.showLove();
                    trackIsLove = true;
                } else {
                    iView.showUnlove();
                    trackIsLove = false;
                }
            }
        });
    }

    private boolean equalsTracks(List<Track> tracks, Track singleTrack) {
        AppLog.log(TAG, "equalsTracks");
        boolean isLove = false;
        for (Track track : tracks) {
            if (track.getName().equals(singleTrack.getName()) && track.getUrl().equals(singleTrack.getUrl())) {
                isLove = true;
                break;
            }
        }
        return isLove;
    }


    @Override
    public void onBtnShareClick() {
        AppLog.log(TAG, "onBtnShareClick");
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, urlYouTube);
        iView.getContext().startActivity(Intent.createChooser(share, "Share link!"));
    }

    @Override
    public void onBtnLoveClick() {
        AppLog.log(TAG, "onBtnLoveClick");
        if (trackIsLove) {
            setUnLoveTrack();
        } else {
            setLoveTrack();
        }
    }

    private void setLoveTrack() {
        AppLog.log(TAG, "setLoveTrack");
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getResultLoveTrack(track).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
            }

            @Override
            public void onNext(Result result) {
                AppLog.log(TAG, "onNext");
                if (result.getStatus() == Result.Status.OK) {
                    trackIsLove = true;
                    iView.showLove();
                }
            }
        });
    }

    private void setUnLoveTrack() {
        AppLog.log(TAG, "setUnLoveTrack");
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getResultUnLoveTrack(track).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
            }

            @Override
            public void onNext(Result result) {
                AppLog.log(TAG, "onNext");
                if (result.getStatus() == Result.Status.OK) {
                    trackIsLove = false;
                    iView.showUnlove();
                }
            }
        });
    }

    @Override
    public void scrobbleTrack() {
        AppLog.log(TAG, "scrobbleTrack");
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getResultTrackScrobble(track).subscribe(new Action1<ScrobbleResult>() {
            @Override
            public void call(ScrobbleResult scrobbleResult) {
                AppLog.log(TAG, "call");
                if (scrobbleResult.getStatus() == Result.Status.OK) {
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        iView = null;
        track = null;
        RxUtils.unsubscribe(subscription);
    }
}
