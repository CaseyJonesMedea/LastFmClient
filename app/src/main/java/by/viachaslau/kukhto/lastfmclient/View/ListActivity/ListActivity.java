package by.viachaslau.kukhto.lastfmclient.View.ListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import by.viachaslau.kukhto.lastfmclient.Presenter.ListActivityPresenter;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.Fragments.ErrorFragmentUser;

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

    private Toolbar toolbar;

    private ImageView btnUpdate;
    private FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initViews();
        Intent intent = getIntent();
        presenter = new ListActivityPresenter(this, this, intent);
    }

    private void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolbar_list);
        btnUpdate = (ImageView)findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        loadFragment = (LinearLayout) findViewById(R.id.progress_load);
        container = (FrameLayout)findViewById(R.id.container_list_activity);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_list_activity, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
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
        switch (view.getId()){
            case R.id.btn_update:
                presenter.onBtnUpdateClick();
                break;
        }
    }
}
