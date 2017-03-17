package by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity;

import android.content.Intent;
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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments.ErrorFragmentUser;

/**
 * Created by VKukh on 27.02.2017.
 */

public class ArtistActivity extends AppCompatActivity implements View.OnClickListener, ArtistActivityIView {

    public static final String TAG = ArtistActivity.class.getSimpleName();

    public static final String ARTIST = "artist";

    private Toolbar toolbar;
    private ImageView btnUpdate;

    private LinearLayout btnArtistInfo;
    private LinearLayout btnArtistLibrary;
    private LinearLayout loadFragment;
    private FrameLayout container;

    private TextView scrobbles;
    private TextView artistName;
    private ImageView logoArtist;

    private ImageLoader imageLoader;

    private ErrorFragmentUser errorFragmentUser;

    private ArtistActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imageLoader = ImageLoader.getInstance();
        Intent intent = getIntent();
        initViews();
        presenter = new ArtistActivityPresenter(this, this, intent);
    }

    private void initViews() {
        scrobbles = (TextView) findViewById(R.id.text_scrobbles);
        artistName = (TextView) findViewById(R.id.text_name);
        logoArtist = (ImageView) findViewById(R.id.img_logo_artist);
        toolbar = (Toolbar) findViewById(R.id.toolbar_artist);
        btnUpdate = (ImageView) toolbar.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        loadFragment = (LinearLayout) findViewById(R.id.progress_load);
        container = (FrameLayout) findViewById(R.id.container_artist_activity);
        btnArtistLibrary = (LinearLayout) findViewById(R.id.ll_library_artist);
        btnArtistLibrary.setOnClickListener(this);
        btnArtistInfo = (LinearLayout) findViewById(R.id.ll_info_artist);
        btnArtistInfo.setOnClickListener(this);
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
        scrobbles.setText(getBaseContext().getString(R.string.scrobbles) + " " + String.valueOf(artist.getPlaycount()));
        imageLoader.displayImage(artist.getImageURL(ImageSize.LARGE), logoArtist);
        artistName.setText(artist.getName());
    }

    @Override
    public void onClick(View v) {
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
        }
    }
}
