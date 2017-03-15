package by.viachaslau.kukhto.lastfmclient.View.WorldChardActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Presenter.WorldChartActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.Fragments.ErrorFragmentUser;

/**
 * Created by kuhto on 07.03.2017.
 */

public class WorldChartActivity extends AppCompatActivity implements WorldChartActivityIView, View.OnClickListener {


    private Toolbar toolbar;

    private ImageView btnUpdate;

    private FrameLayout container;

    private LinearLayout loadFragment;
    private ErrorFragmentUser errorFragmentUser;

    private LinearLayout btnChartArtists;
    private LinearLayout btnChartTracks;
    private WorldChartActivityPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_chart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        presenter = new WorldChartActivityPresenter(this, this);
    }

    private void initViews() {
        container = (FrameLayout) findViewById(R.id.container_chart_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar_world_chart);
        btnUpdate = (ImageView)findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        loadFragment = (LinearLayout) findViewById(R.id.progress_load);
        btnChartArtists = (LinearLayout) findViewById(R.id.ll_chart_artists);
        btnChartTracks = (LinearLayout) findViewById(R.id.ll_chart_tracks);
        btnChartArtists.setOnClickListener(this);
        btnChartTracks.setOnClickListener(this);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
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
        container.setVisibility(View.INVISIBLE);
        loadFragment.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadProgressBar() {
        container.setVisibility(View.VISIBLE);
        loadFragment.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorFragment() {
        if (errorFragmentUser == null) {
            errorFragmentUser = new ErrorFragmentUser();
        }
        showFragment(errorFragmentUser, false, ErrorFragmentUser.TAG);
    }

    @Override
    public void showFragment(Fragment fragment, boolean addToBackStack, String tag) {
        replaceFragment(fragment, addToBackStack, tag);
    }

    @Override
    public void onClick(View view) {
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
}
