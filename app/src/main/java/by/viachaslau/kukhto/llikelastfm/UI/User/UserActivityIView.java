package by.viachaslau.kukhto.llikelastfm.UI.User;


import android.content.Context;
import android.support.v4.app.Fragment;

import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.User;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public interface UserActivityIView {

    void showLoadProgressBar();

    void hideLoadProgressBar();

    void showErrorFragment();

    void showFragment(Fragment fragment, boolean addToBackStack, String tag);

    void initUserFull(User user);

    Context getContext();


}
