package by.viachaslau.kukhto.lastfmclient.UI.WelcomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import javax.inject.Inject;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.UserInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Session;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonPreference;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonSession;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.UserActivity.UserActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.observers.Subscribers;

/**
 * Created by CaseyJones on 25.01.2017.
 */

public class WelcomePresenter implements WelcomeIPresenter {

    public static final String TAG = WelcomePresenter.class.getSimpleName();

    private WelcomeActivityIView iView;

    private Subscription subscription = Subscribers.empty();

    private AlertDialog dialog;

    protected WelcomeModelImpl model;


    public WelcomePresenter(WelcomeModelImpl model) {
        AppLog.log(TAG, "createWelcomePresenter");
        this.model = model;
    }

    @Override
    public void onCreate(WelcomeActivityIView iView) {
        AppLog.log(TAG, "onCreate");
        this.iView = iView;
        initActivity();
    }

    private void initActivity() {
        AppLog.log(TAG, "initActivity");
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getSharedPreferencesUserInfo().subscribe(new Action1<UserInformation>() {
            @Override
            public void call(UserInformation userInformation) {
                AppLog.log(TAG, "call");
                if (userInformation != null) {
                    loadSession(userInformation);
                } else {
                    iView.showButtons();
                }
            }
        });
    }

    private void loadSession(UserInformation userInformation) {
        AppLog.log(TAG, "loadSession");
        iView.showScreenLoad();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getMobileSession(userInformation).subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorInternetMessage();
            }

            @Override
            public void onNext(Session session) {
                AppLog.log(TAG, "onNext");
                if (session != null) {
                    SingletonSession.newInstance(session);
                    SingletonPreference.getInstance().saveUserInfo(userInformation);
                    iView.hideScreenLoad();
                    goToUserActivity(session.getUsername());
                } else {
                    iView.showIncorrectMessage();
                    iView.showButtons();
                }
            }
        });
    }

    private void goToUserActivity(String userName) {
        AppLog.log(TAG, "goToUserActivity");
        Intent intent = new Intent(iView.getContext(), UserActivity.class);
        intent.putExtra(UserActivity.USER_NAME, userName);
        iView.getContext().startActivity(intent);
        ((WelcomeActivity) iView.getContext()).finish();
    }


    @Override
    public void onBtnLogInClick() {
        AppLog.log(TAG, "onBtnLogInClick");
        createLogInAlertDialog();
    }

    @Override
    public void onBtnRegistrationClick() {
        AppLog.log(TAG, "onBtnRegistrationClick");
        Uri address = Uri.parse(iView.getContext().getString(R.string.uri_registration));
        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
        iView.getContext().startActivity(openlinkIntent);
    }


    private void createLogInAlertDialog() {
        AppLog.log(TAG, "createLogInAlertDialog");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(iView.getContext(), R.style.MyAlertDialogStyle);
        View view = ((Activity) iView.getContext()).getLayoutInflater().inflate(R.layout.view_dialog_log_in, null);
        EditText nameUserEnter = (EditText) view.findViewById(R.id.name_user_enter);
        EditText passwordUserEnter = (EditText) view.findViewById(R.id.password_user_enter);
        TextView btnLogIn = (TextView)view.findViewById(R.id.btn_log_in);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInformation information = new UserInformation(nameUserEnter.getText().toString(), passwordUserEnter.getText().toString());
                loadSession(information);
            }
        });
        TextView btnCancel = (TextView)view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.cancel();
            }
        });
        alertDialog.setView(view);
        alertDialog.setCancelable(true);
        dialog = alertDialog.create();
        iView.showLoginDialog(dialog);
    }

    @Override
    public void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        dialog = null;
        iView = null;
        RxUtils.unsubscribe(subscription);
    }

    @Override
    public void onRefresh() {
        AppLog.log(TAG, "onRefresh");
        initActivity();
    }
}
