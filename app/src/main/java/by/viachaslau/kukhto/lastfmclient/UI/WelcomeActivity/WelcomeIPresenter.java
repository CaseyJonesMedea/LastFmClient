package by.viachaslau.kukhto.lastfmclient.UI.WelcomeActivity;

/**
 * Created by CaseyJones on 25.01.2017.
 */

public interface WelcomeIPresenter {

    void onCreate(WelcomeActivityIView iView);

    void onBtnLogInClick();

    void onBtnRegistrationClick();

    void onDestroy();

    void onRefresh();
}
