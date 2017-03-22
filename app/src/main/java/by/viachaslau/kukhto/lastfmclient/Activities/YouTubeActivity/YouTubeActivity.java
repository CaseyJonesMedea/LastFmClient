package by.viachaslau.kukhto.lastfmclient.Activities.YouTubeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.YouTube;
import by.viachaslau.kukhto.lastfmclient.R;

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

    private YouTubeActivityPresenter presenter;

    private YouTubePlayerView youTubeView;

    private ImageView btnShare;

    private YouTubePlayer player;

    private boolean trackIsScrobble;

    private ImageView btnLove;

    private boolean fullScreen;

    private String youTubeCode = "";

    private Track track;

    private int time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
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
        presenter = new YouTubeActivityPresenter(this, youTubeCode, track);
        initViews();
        youTubeView.initialize(YouTube.key, this);
        youTubeView.destroyDrawingCache();
    }


    private void initViews() {
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        btnShare = (ImageView) findViewById(R.id.btn_share);
        btnShare.setOnClickListener(this);
        btnLove = (ImageView) findViewById(R.id.float_btn);
        btnLove.setOnClickListener(this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        player = youTubePlayer;
        player.setPlaybackEventListener(this);
        player.setPlayerStateChangeListener(this);
        player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean _isFullScreen) {
                fullScreen = _isFullScreen;
            }
        });
        player.loadVideo(youTubeCode, time);
        player.play();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACK_IS_SCROBBLE, trackIsScrobble);
        outState.putSerializable(YOUTUBE_TRACK, track);
        outState.putString(YOUTUBE_CODE, youTubeCode);
        outState.putInt(TIMEPAUSE, player.getCurrentTimeMillis());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubeView().initialize(YouTube.key, this);
        }
    }


    private YouTubePlayerView getYouTubeView() {
        return youTubeView;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (fullScreen) {
            player.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void showUnlove() {
        btnLove.setImageResource(R.drawable.unlike);
    }

    @Override
    public void showLove() {
        btnLove.setImageResource(R.drawable.like);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
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
        Log.d(TAG, "onPlaying");
    }

    @Override
    public void onPaused() {
        Log.d(TAG, "onPaused");
    }

    @Override
    public void onStopped() {
        Log.d(TAG, "onStopped");
    }

    @Override
    public void onBuffering(boolean b) {
        Log.d(TAG, "onBuffering");
    }

    @Override
    public void onSeekTo(int i) {
        Log.d(TAG, "onSeekTo");
    }

    @Override
    public void onLoading() {
        Log.d(TAG, "onLoading");
    }

    @Override
    public void onLoaded(String s) {
        Log.d(TAG, "onLoaded" + s);
    }

    @Override
    public void onAdStarted() {
        Log.d(TAG, "onAdStarted");
    }

    @Override
    public void onVideoStarted() {
        Log.d(TAG, "onVideoStarted");
        if (!trackIsScrobble) {
            presenter.scrobbleTrack();
            trackIsScrobble = true;
        }

    }

    @Override
    public void onVideoEnded() {
        Log.d(TAG, "onVideoEnded");
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        Log.d(TAG, "" + errorReason);
    }

}
