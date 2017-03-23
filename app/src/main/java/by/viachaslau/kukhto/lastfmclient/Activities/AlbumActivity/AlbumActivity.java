package by.viachaslau.kukhto.lastfmclient.Activities.AlbumActivity;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments.ErrorFragmentUser;

/**
 * Created by VKukh on 04.03.2017.
 */

public class AlbumActivity extends AppCompatActivity implements AlbumActivityIView, View.OnClickListener {

    public static final String TAG = AlbumActivity.class.getSimpleName();

    public static final String ALBUM_NAME = "albumName";
    public static final String ARTIST_NAME = "artistName";

    @BindView(R.id.text_name_artist)
    TextView nameArtist;
    @BindView(R.id.text_name_album)
    TextView nameAlbum;
    @BindView(R.id.text_scrobbles)
    TextView scrobbles;
    @BindView(R.id.img_logo_album)
    ImageView imgAlbumLogo;

    @BindView(R.id.btn_update)
    ImageView btnUpdate;
    @BindView(R.id.btn_share)
    ImageView btnShare;

    @BindView(R.id.progress_load)
    LinearLayout loadFragment;


    private ErrorFragmentUser errorFragmentUser;
    private AlbumActivityPresenter presenter;
    private ImageLoader imageLoader;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setContentView(R.layout.activity_album);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        initInitialization();
        imageLoader = ImageLoader.getInstance();
        presenter = new AlbumActivityPresenter(this, getIntent());
    }

    private void initInitialization(){
        btnUpdate.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
        AppLog.log(TAG, "replaceFragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_album_activity, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void showLoadProgressBar() {
        AppLog.log(TAG, "showLoadProgressBar");
        loadFragment.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadProgressBar() {
        AppLog.log(TAG, "hideLoadProgressBar");
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
    public void initAlbumFull(Album album) {
        AppLog.log(TAG, "initAlbumFull");
        scrobbles.setText(getBaseContext().getString(R.string.scrobbles) + " " + String.valueOf(album.getPlaycount()));
        imageLoader.displayImage(album.getImageURL(ImageSize.LARGE), imgAlbumLogo);
        nameArtist.setText(album.getArtist());
        nameAlbum.setText(album.getName());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        AppLog.log(TAG, "onClick");
        switch (view.getId()){
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
