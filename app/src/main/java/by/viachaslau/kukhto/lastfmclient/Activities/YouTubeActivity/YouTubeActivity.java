package by.viachaslau.kukhto.lastfmclient.Activities.YouTubeActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import by.viachaslau.kukhto.lastfmclient.Others.YouTube;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 15.03.2017.
 */

public class YouTubeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener,
        YouTubePlayer.PlaybackEventListener, YouTubeActivityIView, View.OnClickListener, YouTubePlayer.PlayerStateChangeListener {

    public static final String TAG = YouTubeActivity.class.getSimpleName();

    public static final String YOUTUBE_CODE = "codeForYouTube";
    public static final String YOUTUBE_TRACK = "youtubeTrack";

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerSupportFragment youTubePlayerSupportFragment;

    private YouTubeActivityPresenter presenter;

    private ImageView btnShare;

    private ImageView btnLove;

    private String youTubeCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        youTubeCode = intent.getStringExtra(YOUTUBE_CODE);
        initViews();
        presenter = new YouTubeActivityPresenter(this, this, intent);
        youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        youTubePlayerSupportFragment.initialize(YouTube.key, this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.youtube_container, youTubePlayerSupportFragment, youTubePlayerSupportFragment.getTag());
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initViews() {
        btnShare = (ImageView) findViewById(R.id.btn_share);
        btnShare.setOnClickListener(this);
        btnLove = (ImageView) findViewById(R.id.float_btn);
        btnLove.setOnClickListener(this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.setPlaybackEventListener(this);
            youTubePlayer.setPlayerStateChangeListener(this);
            youTubePlayer.cueVideo(youTubeCode);
        }
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
            // Retry initialization if user performed a recovery action
            getYouTubePlayerSupportFragment().initialize(YouTube.key, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected YouTubePlayerSupportFragment getYouTubePlayerSupportFragment() {
        return youTubePlayerSupportFragment;
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
