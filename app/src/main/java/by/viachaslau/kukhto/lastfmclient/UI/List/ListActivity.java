package by.viachaslau.kukhto.lastfmclient.UI.List;

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


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.App;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.User.UserActivityFragments.ErrorFragmentUser;

/**
 * Created by VKukh on 04.03.2017.
 */

public class ListActivity extends AppCompatActivity implements ListActivityIView, View.OnClickListener {

    public static final String TAG = ListActivity.class.getSimpleName();

    public static final String NAME_USER = "nameUser";
    public static final String TITLE = "title";

    @Inject
    ListActivityPresenter presenter;

    private ErrorFragmentUser errorFragmentUser;

    @BindView(R.id.progress_load)
    LinearLayout loadFragment;

    @BindView(R.id.btn_update)
    ImageView btnUpdate;

    private Animation rotation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setContentView(R.layout.activity_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        App.getComponent().inject(this);
        initCommercial();
        ButterKnife.bind(this);
        initInitialize();
        presenter.onCreate(this, getIntent());
    }

    private void initCommercial(){
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
    }

    private void initInitialize() {
        AppLog.log(TAG, "initInitialize");
        btnUpdate.setOnClickListener(this);
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
        AppLog.log(TAG, "replaceFragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_list_activity, fragment, tag);
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
