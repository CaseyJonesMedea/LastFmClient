package by.viachaslau.kukhto.lastfmclient.Activities.TrackActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.YouTube;
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

    private Context context;


    private String url;

    private Track track;

    private Subscription subscription = Subscribers.empty();
    private TrackActivityIView iView;

    public TrackActivityPresenter(Context context, TrackActivityIView iView, Intent intent) {
        this.iView = iView;
        this.context = context;
        track = (Track) intent.getSerializableExtra(TrackActivity.TRACK_URL);
        url = track.getUrl();
        loadPage(url);
    }

    private void loadPage(String url) {
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getYouTubeUrl(url).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                iView.closeActivity();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, context.getString(R.string.error_not_video), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(String codeYouTube) {
                Intent intent = new Intent(context, YouTubeActivity.class);
                intent.putExtra(YouTubeActivity.YOUTUBE_CODE, codeYouTube);
                intent.putExtra(YouTubeActivity.YOUTUBE_TRACK, track);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        iView = null;
        context = null;
        track = null;
        url = null;
        RxUtils.unsubscribe(subscription);
    }
}
