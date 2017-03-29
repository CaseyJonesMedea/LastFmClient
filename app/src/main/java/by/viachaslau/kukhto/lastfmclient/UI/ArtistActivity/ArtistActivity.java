package by.viachaslau.kukhto.lastfmclient.UI.ArtistActivity;

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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.UserActivity.UserActivityFragments.ErrorFragmentUser;

/**
 * Created by VKukh on 27.02.2017.
 */

public class ArtistActivity extends AppCompatActivity implements View.OnClickListener, ArtistActivityIView {

    public static final String TAG = ArtistActivity.class.getSimpleName();

    public static final String ARTIST = "artist";

    @BindView(R.id.btn_update)
    ImageView btnUpdate;
    @BindView(R.id.btn_share)
    ImageView btnShare;

    @BindView(R.id.ll_info_artist)
    LinearLayout btnArtistInfo;
    @BindView(R.id.ll_library_artist)
    LinearLayout btnArtistLibrary;
    @BindView(R.id.progress_load)
    LinearLayout loadFragment;

    @BindView(R.id.text_scrobbles)
    TextView scrobbles;
    @BindView(R.id.text_name)
    TextView artistName;
    @BindView(R.id.img_logo_artist)
    ImageView logoArtist;

    private ImageLoader imageLoader;
    private ErrorFragmentUser errorFragmentUser;
    private ArtistActivityPresenter presenter;

    private Animation rotation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setContentView(R.layout.activity_artist);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imageLoader = ImageLoader.getInstance();
        ButterKnife.bind(this);
        initInitialize();
        presenter = new ArtistActivityPresenter(this, getIntent());
    }

    private void initInitialize() {
        AppLog.log(TAG, "initInitialize");
        btnUpdate.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnArtistLibrary.setOnClickListener(this);
        btnArtistInfo.setOnClickListener(this);
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_artist_activity, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void initArtistFull(Artist artist) {
        AppLog.log(TAG, "initArtistFull");
        scrobbles.setText(getBaseContext().getString(R.string.scrobbles) + " " + String.valueOf(artist.getPlaycount()));
        imageLoader.displayImage(artist.getImageURL(ImageSize.LARGE), logoArtist);
        artistName.setText(artist.getName());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        AppLog.log(TAG, "onClick");
        switch (v.getId()) {
            case R.id.ll_info_artist:
                presenter.onBtnInfoClick();
                break;
            case R.id.ll_library_artist:
                presenter.onBtnLibraryClick();
                break;
            case R.id.btn_update:
                presenter.onBtnUpdateClick();
                break;
            case R.id.btn_share:
                presenter.onBtnShareClick();
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
