package by.viachaslau.kukhto.llikelastfm.UI.YouTube;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.llikelastfm.Others.App;
import by.viachaslau.kukhto.llikelastfm.Others.Model.AppLog;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.llikelastfm.Others.SingletonFonts;
import by.viachaslau.kukhto.llikelastfm.Others.YouTube;
import by.viachaslau.kukhto.llikelastfm.R;

/**
 * Created by kuhto on 15.03.2017.
 */

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,
        YouTubePlayer.PlaybackEventListener, YouTubeActivityIView, View.OnClickListener, YouTubePlayer.PlayerStateChangeListener {

    public static final String TAG = YouTubeActivity.class.getSimpleName();

    public static final String YOUTUBE_CODE = "codeForYouTube";
    public static final String YOUTUBE_TRACK = "youtubeTrack";

    public static final String TIMEPAUSE = "time";
    public static final String TRACK_IS_SCROBBLE = "scrobble";

    private static final int RECOVERY_REQUEST = 1;

    @Inject
    protected YouTubeActivityPresenter presenter;

    @BindView(R.id.youtube_view)
    YouTubePlayerView youTubeView;
    @BindView(R.id.btn_share)
    ImageView btnShare;

    private YouTubePlayer player;

    private boolean trackIsScrobble;
    @BindView(R.id.float_btn)
    ImageView btnLove;
    @BindView(R.id.i)
    TextView i;
    @BindView(R.id.lastfm)
    TextView lastfm;

    private boolean fullScreen;

    private String youTubeCode = "";

    private Track track;

    private int time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        AppLog.log(TAG, "onCreate" + "savedInstanceState= " + savedInstanceState);
        App.getComponent().inject(this);
        if (savedInstanceState != null) {
            trackIsScrobble = savedInstanceState.getBoolean(TRACK_IS_SCROBBLE);
            youTubeCode = savedInstanceState.getString(YOUTUBE_CODE);
            track = (Track) savedInstanceState.getSerializable(YOUTUBE_TRACK);
            time = savedInstanceState.getInt(TIMEPAUSE);
        } else {
            Intent intent = getIntent();
            youTubeCode = intent.getStringExtra(YOUTUBE_CODE);
            track = (Track) intent.getSerializableExtra(YOUTUBE_TRACK);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            time = 0;
            trackIsScrobble = false;
        }
        presenter.onCreate(this, youTubeCode, track);
        ButterKnife.bind(this);
        initialize();
        youTubeView.initialize(YouTube.key, this);
        youTubeView.destroyDrawingCache();
    }


    private void initialize() {
        AppLog.log(TAG, "initialize");
        btnShare.setOnClickListener(this);
        btnLove.setOnClickListener(this);
        i.setTypeface(SingletonFonts.getInstance(this).getFont3());
        lastfm.setTypeface(SingletonFonts.getInstance(this).getFont3());
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        AppLog.log(TAG, "onInitializationSuccess");
        player = youTubePlayer;
        player.setPlaybackEventListener(this);
        player.setPlayerStateChangeListener(this);
        player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean _isFullScreen) {
                AppLog.log(TAG, "onFullscreen");
                fullScreen = _isFullScreen;
            }
        });
        player.loadVideo(youTubeCode, time);
        player.play();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        AppLog.log(TAG, "onSaveInstanceState");
        outState.putBoolean(TRACK_IS_SCROBBLE, trackIsScrobble);
        outState.putSerializable(YOUTUBE_TRACK, track);
        outState.putString(YOUTUBE_CODE, youTubeCode);
        outState.putInt(TIMEPAUSE, player.getCurrentTimeMillis());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        AppLog.log(TAG, "onInitializationFailure");
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        AppLog.log(TAG, "onActivityResult");
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubeView().initialize(YouTube.key, this);
        }
    }


    private YouTubePlayerView getYouTubeView() {
        AppLog.log(TAG, "getYouTubeView");
        return youTubeView;
    }

    @Override
    protected void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AppLog.log(TAG, "onBackPressed");
        if (fullScreen) {
            player.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void showUnlove() {
        AppLog.log(TAG, "showUnlove");
        btnLove.setImageResource(R.drawable.unlike);
    }

    @Override
    public void showLove() {
        AppLog.log(TAG, "showLove");
        btnLove.setImageResource(R.drawable.like);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        AppLog.log(TAG, "onClick");
        switch (v.getId()) {
            case R.id.btn_share:
                presenter.onBtnShareClick();
                break;
            case R.id.float_btn:
                presenter.onBtnLoveClick();
                break;
        }
    }

    @Override
    public void onPlaying() {
        AppLog.log(TAG, "onPlaying");
    }

    @Override
    public void onPaused() {
        AppLog.log(TAG, "onPaused");
    }

    @Override
    public void onStopped() {
        AppLog.log(TAG, "onStopped");
    }

    @Override
    public void onBuffering(boolean b) {
        AppLog.log(TAG, "onBuffering");
    }

    @Override
    public void onSeekTo(int i) {
        AppLog.log(TAG, "onSeekTo");
    }

    @Override
    public void onLoading() {
        AppLog.log(TAG, "onLoading");
    }

    @Override
    public void onLoaded(String s) {
        AppLog.log(TAG, "onLoaded");
    }

    @Override
    public void onAdStarted() {
        AppLog.log(TAG, "onAdStarted");
    }

    @Override
    public void onVideoStarted() {
        AppLog.log(TAG, "onVideoStarted");
        if (!trackIsScrobble) {
            presenter.scrobbleTrack();
            trackIsScrobble = true;
        }

    }

    @Override
    public void onVideoEnded() {
        AppLog.log(TAG, "onVideoEnded");
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        AppLog.log(TAG, "onError " + errorReason);
    }

}
