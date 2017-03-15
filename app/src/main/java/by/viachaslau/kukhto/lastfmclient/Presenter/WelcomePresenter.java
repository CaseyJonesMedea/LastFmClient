package by.viachaslau.kukhto.lastfmclient.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;




import by.viachaslau.kukhto.lastfmclient.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Model.modelApp.UserInformation;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Session;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonPreference;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonSession;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.UserActivity;
import by.viachaslau.kukhto.lastfmclient.View.WelcomeActivity.WelcomeActivity;
import by.viachaslau.kukhto.lastfmclient.View.WelcomeActivity.WelcomeActivityIView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observers.Subscribers;

/**
 * Created by CaseyJones on 25.01.2017.
 */

public class WelcomePresenter implements WelcomeIPresenter {

    private Context context;
    private WelcomeActivityIView iView;
    private Subscription subscription = Subscribers.empty();


    public WelcomePresenter(Context context, WelcomeActivityIView iView) {
        this.context = context;
        this.iView = iView;

        initActivity();
    }

    private void initActivity() {
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getSharedPreferencesUserInfo().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<UserInformation>() {
            @Override
            public void call(UserInformation userInformation) {
                if (userInformation != null) {
                    loadSession(userInformation);
                } else {
                    iView.showButtons();
                }
            }
        });
    }

    private void loadSession(UserInformation userInformation) {
        iView.showScreenLoad();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = ModelImpl.getModel().getMobileSession(userInformation).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Session>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorInternetMessage();
            }

            @Override
            public void onNext(Session session) {
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
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(UserActivity.USER_NAME, userName);
        context.startActivity(intent);
        ((WelcomeActivity) context).finish();
    }

    @Override
    public void onBtnLogInClick() {
        createLogInAlertDialog();
    }

    @Override
    public void onBtnRegistrationClick() {
        Uri address = Uri.parse(context.getString(R.string.uri_registration));
        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
        context.startActivity(openlinkIntent);
    }

    private void createLogInAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.view_dialog_log_in, null);
        EditText nameUserEnter = (EditText) view.findViewById(R.id.name_user_enter);
        EditText passwordUserEnter = (EditText) view.findViewById(R.id.password_user_enter);
        alertDialog.setView(view);

        alertDialog.setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                UserInformation information = new UserInformation(nameUserEnter.getText().toString(), passwordUserEnter.getText().toString());
                loadSession(information);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });

        alertDialog.setCancelable(true);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {

            }
        });
        AlertDialog dialog = alertDialog.create();
        iView.showLoginDialog(dialog);
    }
}
