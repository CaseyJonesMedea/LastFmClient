package by.viachaslau.kukhto.lastfmclient.UI.Track;

import android.content.Intent;
import android.widget.Toast;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.YouTube.YouTubeActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.Subscribers;

/**
 * Created by kuhto on 15.03.2017.
 */

public class TrackActivityPresenter implements TrackActivityIPresenter {

    public static final String TAG = TrackActivityPresenter.class.getSimpleName();

    private String url;

    private Track track;

    private Subscription subscription = Subscribers.empty();
    private TrackActivityIView iView;

    protected TrackModelImpl model;

    public TrackActivityPresenter(TrackModelImpl model) {
        AppLog.log(TAG, "createTrackActivityPresenter");
        this.model = model;
    }

    @Override
    public void onCreate(TrackActivityIView iView, Intent intent) {
        AppLog.log(TAG, "onCreate");
        this.iView = iView;
        track = (Track) intent.getSerializableExtra(TrackActivity.TRACK);
        url = track.getUrl();
        loadPage(url);
    }

    private void loadPage(String url) {
        AppLog.log(TAG, "loadPage");
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getYouTubeUrl(url).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
                iView.closeActivity();
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                Toast.makeText(iView.getContext(), iView.getContext().getString(R.string.error_not_video), Toast.LENGTH_LONG).show();
                iView.closeActivity();
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
