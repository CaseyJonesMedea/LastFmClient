package by.viachaslau.kukhto.lastfmclient.UI.Welcome;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.App;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonFonts;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by CaseyJones on 25.01.2017.
 */

public class WelcomeActivity extends AppCompatActivity implements WelcomeActivityIView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = WelcomeActivity.class.getSimpleName();

    @BindView(R.id.btn_log_in)
    Button btnLogIn;
    @BindView(R.id.btn_registration)
    Button btnRegistration;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe;
    @BindView(R.id.load)
    View fragmentLoad;
    @BindView(R.id.i)
    TextView i;
    @BindView(R.id.lastfm)
    TextView lastfm;

    @Inject
    protected WelcomePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setContentView(R.layout.activity_welcome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        App.getComponent().inject(this);
        ButterKnife.bind(this);
        initinitialize();
        presenter.onCreate(this);
    }

    private void initinitialize() {
        AppLog.log(TAG, "initinitialize");
        i.setTypeface(SingletonFonts.getInstance(this).getFont3());
        lastfm.setTypeface(SingletonFonts.getInstance(this).getFont3());
        swipe.setOnRefreshListener(this);
        btnLogIn.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
    }

    @Override
    public void showLoginDialog(AlertDialog alertDialog) {
        AppLog.log(TAG, "showLoginDialog");
        alertDialog.show();
    }

    @Override
    public void showScreenLoad() {
        AppLog.log(TAG, "showScreenLoad");
        fragmentLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideScreenLoad() {
        AppLog.log(TAG, "hideScreenLoad");
        fragmentLoad.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorInternetMessage() {
        AppLog.log(TAG, "showErrorInternetMessage");
        Toast.makeText(this, getString(R.string.internet_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIncorrectMessage() {
        AppLog.log(TAG, "showIncorrectMessage");
        Toast.makeText(this, getString(R.string.incorrect_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showButtons() {
        AppLog.log(TAG, "showButtons");
        btnLogIn.setVisibility(View.VISIBLE);
        btnRegistration.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtons() {
        AppLog.log(TAG, "hideButtons");
        btnLogIn.setVisibility(View.INVISIBLE);
        btnRegistration.setVisibility(View.INVISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void onClick(View view) {
        AppLog.log(TAG, "onClick");
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
        AppLog.log(TAG, "onRefresh");
        presenter.onRefresh();
    }

    @Override
    protected void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        presenter.onDestroy();
        super.onDestroy();
    }
}
