package by.viachaslau.kukhto.lastfmclient.Activities.UserActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.User;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments.ErrorFragmentUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public class UserActivity extends AppCompatActivity implements UserActivityIView, View.OnClickListener {

    public static final String TAG = UserActivity.class.getSimpleName();


    public static final String USER_NAME = "userName";

    private TextView name;
    private TextView scrobbles;
    private CircleImageView imgUserLogo;

    private Toolbar toolbar;
    private ImageView btnExitAccount;
    private ImageView btnUpdate;
    private ImageView btnSearch;
    private LinearLayout btnFriends;
    private LinearLayout btnUserChart;
    private LinearLayout btnHome;


    private LinearLayout loadFragment;
    private ErrorFragmentUser errorFragmentUser;
    private UserActivityPresenter presenter;
    private ImageLoader imageLoader;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        imageLoader = ImageLoader.getInstance();
        Intent intent = getIntent();
        presenter = new UserActivityPresenter(this, intent);
    }

    private void initViews() {
        name = (TextView) findViewById(R.id.text_name);
        scrobbles = (TextView) findViewById(R.id.text_scrobbles);
        imgUserLogo = (CircleImageView) findViewById(R.id.img_logo_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnUpdate = (ImageView) toolbar.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        btnSearch = (ImageView)toolbar.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
        btnExitAccount = (ImageView)toolbar.findViewById(R.id.btn_exit_account);
        btnExitAccount.setOnClickListener(this);
        btnHome = (LinearLayout)findViewById(R.id.ll_home);
        btnHome.setOnClickListener(this);
        btnFriends = (LinearLayout) findViewById(R.id.ll_friends);
        btnFriends.setOnClickListener(this);
        btnUserChart = (LinearLayout) findViewById(R.id.ll_chart_user);
        btnUserChart.setOnClickListener(this);
        loadFragment = (LinearLayout) findViewById(R.id.progress_load);
    }


    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_user_activity, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void showLoadProgressBar() {
        loadFragment.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void hideLoadProgressBar() {
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
    public void initUserFull(User user) {
        scrobbles.setText(getBaseContext().getString(R.string.scrobbles) + " " + String.valueOf(user.getPlaycount()));
        imageLoader.displayImage(user.getImageURL(ImageSize.LARGE), imgUserLogo);
        name.setText(user.getName());
    }

    @Override
    public Context getContext(){
        return this;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit_account:
                presenter.onBtnExitClick();
                break;
            case R.id.btn_update:
                presenter.onBtnUpdateClick();
                break;
            case R.id.btn_search:
                presenter.onBtnSearchClick();
                break;
            case R.id.ll_home:
                presenter.onBtnHomeClick();
                break;
            case R.id.ll_friends:
                presenter.onBtnFriendsClick();
                break;
            case R.id.ll_chart_user:
                presenter.onBtnUserChartClick();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
