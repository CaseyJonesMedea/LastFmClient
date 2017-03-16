package by.viachaslau.kukhto.lastfmclient.View.WelcomeActivity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import by.viachaslau.kukhto.lastfmclient.Presenter.WelcomePresenter;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by CaseyJones on 25.01.2017.
 */

public class WelcomeActivity extends AppCompatActivity implements WelcomeActivityIView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private Button btnLogIn;
    private Button btnRegistration;
    private WelcomePresenter presenter;

    private SwipeRefreshLayout swipe;
    private View fragmentLoad;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        presenter = new WelcomePresenter(this, this);
    }

    private void initViews() {
        btnLogIn = (Button) findViewById(R.id.btn_log_in);
        btnRegistration = (Button) findViewById(R.id.btn_registration);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipe.setOnRefreshListener(this);
        fragmentLoad = findViewById(R.id.load);
        btnLogIn.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
    }

    @Override
    public void showLoginDialog(AlertDialog alertDialog) {
        alertDialog.show();
    }

    @Override
    public void showScreenLoad() {
        fragmentLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideScreenLoad() {
        fragmentLoad.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorInternetMessage() {
        Toast.makeText(this, getString(R.string.internet_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIncorrectMessage() {
        Toast.makeText(this, getString(R.string.incorrect_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showButtons() {
        btnLogIn.setVisibility(View.VISIBLE);
        btnRegistration.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtons() {
        btnLogIn.setVisibility(View.INVISIBLE);
        btnRegistration.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_log_in:
                presenter.onBtnLogInClick();
                break;
            case R.id.btn_registration:
                presenter.onBtnRegistrationClick();
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

}
