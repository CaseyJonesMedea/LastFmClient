package by.viachaslau.kukhto.lastfmclient.UI.WorldChardActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.UserActivity.UserActivityFragments.ErrorFragmentUser;

/**
 * Created by kuhto on 07.03.2017.
 */

public class WorldChartActivity extends AppCompatActivity implements WorldChartActivityIView, View.OnClickListener {

    public static final String TAG = WorldChartActivity.class.getSimpleName();

    @BindView(R.id.btn_update)
    ImageView btnUpdate;
    @BindView(R.id.progress_load)
    LinearLayout loadFragment;

    private ErrorFragmentUser errorFragmentUser;

    @BindView(R.id.ll_chart_artists)
    LinearLayout btnChartArtists;
    @BindView(R.id.ll_chart_tracks)
    LinearLayout btnChartTracks;

    private WorldChartActivityPresenter presenter;

    private Animation rotation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setContentView(R.layout.activity_world_chart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        initialize();
        presenter = new WorldChartActivityPresenter(this);
    }

    private void initialize() {
        AppLog.log(TAG, "initialize");
        btnUpdate.setOnClickListener(this);
        btnChartArtists.setOnClickListener(this);
        btnChartTracks.setOnClickListener(this);
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
        AppLog.log(TAG, "replaceFragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_chart_activity, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void showLoadProgressBar() {
        AppLog.log(TAG, "showLoadProgressBar");
        btnUpdate.startAnimation(rotation);
        loadFragment.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadProgressBar() {
        AppLog.log(TAG, "hideLoadProgressBar");
        rotation.cancel();
        loadFragment.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorFragment() {
        AppLog.log(TAG, "showErrorFragment");
        if (errorFragmentUser == null) {
            errorFragmentUser = new ErrorFragmentUser();
        }
        showFragment(errorFragmentUser, false, ErrorFragmentUser.TAG);
    }

    @Override
    public void showFragment(Fragment fragment, boolean addToBackStack, String tag) {
        AppLog.log(TAG, "showFragment");
        replaceFragment(fragment, addToBackStack, tag);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        AppLog.log(TAG, "onClick");
        switch (view.getId()) {
            case R.id.ll_chart_artists:
                presenter.onBtnChartArtistClick();
                break;
            case R.id.ll_chart_tracks:
                presenter.onBtnChartTracksClick();
                break;
            case R.id.btn_update:
                presenter.onBtnUpdateClick();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        presenter.onDestroy();
        super.onDestroy();
    }
}
