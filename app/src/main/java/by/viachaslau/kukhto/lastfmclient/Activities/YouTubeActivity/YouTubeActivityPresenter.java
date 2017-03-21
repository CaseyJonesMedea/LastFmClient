package by.viachaslau.kukhto.lastfmclient.Activities.YouTubeActivity;

import android.content.Context;
import android.content.Intent;

import java.util.List;


import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Result;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.scrobble.ScrobbleResult;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonSession;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observers.Subscribers;

/**
 * Created by VKukh on 19.03.2017.
 */

public class YouTubeActivityPresenter implements YouTubeActivityIPresenter {

    private YouTubeActivityIView iView;

    private String urlYouTube;

    private Context context;

    private boolean trackIsLove;

    private boolean trackIsScrobble = false;

    private Track track;

    private Subscription subscription = Subscribers.empty();


    public YouTubeActivityPresenter(Context context, YouTubeActivityIView iView, Intent intent) {
        this.context = context;
        this.iView = iView;
        initialize(intent);
    }

    private void initialize(Intent intent) {
        String code = intent.getStringExtra(YouTubeActivity.YOUTUBE_CODE);
        urlYouTube = "https://www.youtube.com/watch?v=" + code;
        track = (Track) intent.getSerializableExtra(YouTubeActivity.YOUTUBE_TRACK);

        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getLovedTracksJson(SingletonSession.getInstance().getSession().getUsername()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Track>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Track> tracks) {
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
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, urlYouTube);
        context.startActivity(Intent.createChooser(share, "Share link!"));
    }

    @Override
    public void onBtnLoveClick() {
        if (trackIsLove) {
            setUnLoveTrack();
        } else {
            setLoveTrack();
        }
    }

    private void setLoveTrack() {
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getResultLoveTrack(track).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                if (result.getStatus() == Result.Status.OK) {
                    trackIsLove = true;
                    iView.showLove();
                }
            }
        });
    }

    private void setUnLoveTrack() {
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getResultUnLoveTrack(track).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                if (result.getStatus() == Result.Status.OK) {
                    trackIsLove = false;
                    iView.showUnlove();
                }
            }
        });
    }

    @Override
    public void scrobbleTrack() {
        if (!trackIsScrobble) {
            if (subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
            subscription = ModelImpl.getModel().getResultTrackScrobble(track).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ScrobbleResult>() {
                @Override
                public void call(ScrobbleResult scrobbleResult) {
                    if (scrobbleResult.getStatus() == Result.Status.OK) {
                        trackIsScrobble = true;
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        context = null;
        iView = null;
        track = null;
        RxUtils.unsubscribe(subscription);
    }
}
