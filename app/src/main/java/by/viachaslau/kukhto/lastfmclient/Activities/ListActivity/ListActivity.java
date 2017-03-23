package by.viachaslau.kukhto.lastfmclient.Activities.ListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments.ErrorFragmentUser;

/**
 * Created by VKukh on 04.03.2017.
 */

public class ListActivity extends AppCompatActivity implements ListActivityIView, View.OnClickListener {

    public static final String TAG = ListActivity.class.getSimpleName();


    public static final String NAME_USER = "nameUser";
    public static final String TITLE = "title";


    private ListActivityPresenter presenter;

    private ErrorFragmentUser errorFragmentUser;

    @BindView(R.id.progress_load)
    LinearLayout loadFragment;

    @BindView(R.id.btn_update)
    ImageView btnUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        initInitialize();
        presenter = new ListActivityPresenter(this, getIntent());
    }

    private void initInitialize() {
        AppLog.log(TAG, "initInitialize");
        btnUpdate.setOnClickListener(this);
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
