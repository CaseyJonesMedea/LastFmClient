package by.viachaslau.kukhto.lastfmclient.View.AlbumActivity;

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

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Presenter.AlbumActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.Fragments.ErrorFragmentUser;

/**
 * Created by VKukh on 04.03.2017.
 */

public class AlbumActivity extends AppCompatActivity implements AlbumActivityIView, View.OnClickListener {

    public static final String ALBUM_NAME = "albumName";
    public static final String ARTIST_NAME = "artistName";

    private TextView nameArtist;
    private TextView nameAlbum;
    private TextView scrobbles;
    private ImageView imgAlbumLogo;

    private Toolbar toolbar;

    private ImageView btnUpdate;

    private FrameLayout container;

    private LinearLayout loadFragment;
    private ErrorFragmentUser errorFragmentUser;
    private AlbumActivityPresenter presenter;
    private ImageLoader imageLoader;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        imageLoader = ImageLoader.getInstance();
        Intent intent = getIntent();
        presenter = new AlbumActivityPresenter(this, this, intent);
    }

    private void initViews(){
        imgAlbumLogo = (ImageView)findViewById(R.id.img_logo_album);
        nameArtist = (TextView)findViewById(R.id.text_name_artist);
        nameAlbum = (TextView)findViewById(R.id.text_name_album);
        scrobbles = (TextView)findViewById(R.id.text_scrobbles);
        container = (FrameLayout)findViewById(R.id.container_album_activity);
        toolbar = (Toolbar)findViewById(R.id.toolbar_album);
        btnUpdate = (ImageView)toolbar.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        loadFragment = (LinearLayout) findViewById(R.id.progress_load);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
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
    public void initAlbumFull(Album album) {
        scrobbles.setText(getBaseContext().getString(R.string.scrobbles) + " " + String.valueOf(album.getPlaycount()));
        imageLoader.displayImage(album.getImageURL(ImageSize.LARGE), imgAlbumLogo);
        nameArtist.setText(album.getArtist());
        nameAlbum.setText(album.getName());
    }

    @Override
    public void onClick(View view) {
        presenter.onBtnUpdateClick();
    }
}
