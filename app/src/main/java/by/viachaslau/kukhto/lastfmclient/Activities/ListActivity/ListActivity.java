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


import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments.ErrorFragmentUser;

/**
 * Created by VKukh on 04.03.2017.
 */

public class ListActivity extends AppCompatActivity implements ListActivityIView, View.OnClickListener{

    public static final String TAG = ListActivity.class.getSimpleName();


    public static final String NAME_USER = "nameUser";
    public static final String TITLE = "title";


    private ListActivityPresenter presenter;

    private ErrorFragmentUser errorFragmentUser;
    private LinearLayout loadFragment;

    private ImageView btnUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initViews();
        Intent intent = getIntent();
        presenter = new ListActivityPresenter(this, intent);
    }

    private void initViews() {
        btnUpdate = (ImageView)findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        loadFragment = (LinearLayout) findViewById(R.id.progress_load);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
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
        loadFragment.setVisibility(View.VISIBLE);
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
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_update:
                presenter.onBtnUpdateClick();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
