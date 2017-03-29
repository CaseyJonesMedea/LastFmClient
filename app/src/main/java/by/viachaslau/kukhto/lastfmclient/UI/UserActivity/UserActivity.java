package by.viachaslau.kukhto.lastfmclient.UI.UserActivity;

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
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.User;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.UserActivity.UserActivityFragments.ErrorFragmentUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public class UserActivity extends AppCompatActivity implements UserActivityIView, View.OnClickListener {

    public static final String TAG = UserActivity.class.getSimpleName();
    public static final String USER_NAME = "userName";

    @BindView(R.id.text_name)
    TextView name;
    @BindView(R.id.text_scrobbles)
    TextView scrobbles;
    @BindView(R.id.img_logo_user)
    CircleImageView imgUserLogo;
    @BindView(R.id.btn_exit_account)
    ImageView btnExitAccount;
    @BindView(R.id.btn_update)
    ImageView btnUpdate;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.ll_friends)
    LinearLayout btnFriends;
    @BindView(R.id.ll_chart_user)
    LinearLayout btnUserChart;
    @BindView(R.id.ll_home)
    LinearLayout btnHome;
    @BindView(R.id.progress_load)
    LinearLayout loadFragment;

    private ErrorFragmentUser errorFragmentUser;
    private UserActivityPresenter presenter;
    private ImageLoader imageLoader;

    private Animation rotation;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setContentView(R.layout.activity_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        initialize();
        imageLoader = ImageLoader.getInstance();
        presenter = new UserActivityPresenter(this, getIntent());
    }

    private void initialize() {
        AppLog.log(TAG, "initialize");
        btnUpdate.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnExitAccount.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnFriends.setOnClickListener(this);
        btnUserChart.setOnClickListener(this);
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
    }


    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
        AppLog.log(TAG, "replaceFragment");
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
    public void initUserFull(User user) {
        AppLog.log(TAG, "initUserFull");
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
        AppLog.log(TAG, "onClick");
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
        AppLog.log(TAG, "onDestroy");
        presenter.onDestroy();
        super.onDestroy();
    }
}
