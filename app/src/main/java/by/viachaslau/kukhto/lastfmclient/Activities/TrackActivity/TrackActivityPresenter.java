package by.viachaslau.kukhto.lastfmclient.Activities.TrackActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.YouTubeActivity.YouTubeActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;

/**
 * Created by kuhto on 15.03.2017.
 */

public class TrackActivityPresenter implements TrackActivityIPresenter{

    public static final String TAG = TrackActivityPresenter.class.getSimpleName();

    private String url;

    private Track track;

    private Subscription subscription = Subscribers.empty();
    private TrackActivityIView iView;

    public TrackActivityPresenter(TrackActivityIView iView, Intent intent) {
        AppLog.log(TAG, "createTrackActivityPresenter");
        this.iView = iView;
        track = (Track) intent.getSerializableExtra(TrackActivity.TRACK);
        url = track.getUrl();
        loadPage(url);
    }

    private void loadPage(String url) {
        AppLog.log(TAG, "loadPage");
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getYouTubeUrl(url).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
                iView.closeActivity();
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                Toast.makeText( iView.getContext(), iView.getContext().getString(R.string.error_not_video), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(String codeYouTube) {
                AppLog.log(TAG, "onNext");
                Intent intent = new Intent(iView.getContext(), YouTubeActivity.class);
                intent.putExtra(YouTubeActivity.YOUTUBE_CODE, codeYouTube);
                intent.putExtra(YouTubeActivity.YOUTUBE_TRACK, track);
                iView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        iView = null;
        track = null;
        url = null;
        RxUtils.unsubscribe(subscription);
    }
}
