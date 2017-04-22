package by.viachaslau.kukhto.llikelastfm.UI.Welcome;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by CaseyJones on 25.01.2017.
 */

public interface WelcomeActivityIView {

    void showLoginDialog(AlertDialog alertDialog);

    void showScreenLoad();

    void hideScreenLoad();

    void showErrorInternetMessage();

    void showIncorrectMessage();

    void showButtons();

    void hideButtons();

    Context getContext();

}
