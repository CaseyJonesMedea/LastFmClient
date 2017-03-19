package by.viachaslau.kukhto.lastfmclient.Activities.YouTubeActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import by.viachaslau.kukhto.lastfmclient.Others.YouTube;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 15.03.2017.
 */

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubeActivityIView, View.OnClickListener {

    public static final String YOUTUBE_CODE = "codeForYouTube";
    public static final String YOUTUBE_TRACK = "youtubeTrack";

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView player;

    private YouTubeActivityPresenter presenter;

    private ImageView btnShare;

    private FloatingActionButton btnLove;

    private String youTubeCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        youTubeCode = intent.getStringExtra(YOUTUBE_CODE);
        initViews();
        presenter = new YouTubeActivityPresenter(this, this, intent);
        player.initialize(YouTube.key, this);

    }

    private void initViews() {
        player = (YouTubePlayerView) findViewById(R.id.youtube_view);
        btnShare = (ImageView) findViewById(R.id.btn_share);
        btnShare.setOnClickListener(this);
        btnLove = (FloatingActionButton)findViewById(R.id.float_btn);
        btnLove.setOnClickListener(this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
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
            getYouTubePlayerProvider().initialize(YouTube.key, this);
        }
    }


    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return player;
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
}
