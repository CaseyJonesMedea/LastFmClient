package by.viachaslau.kukhto.lastfmclient.UI.List;


import android.content.Context;
import android.support.v4.app.Fragment;


/**
 * Created by VKukh on 04.03.2017.
 */

public interface ListActivityIView {

    void showLoadProgressBar();

    void hideLoadProgressBar();

    void showErrorFragment();

    void showFragment(Fragment fragment, boolean addToBackStack, String tag);

    Context getContext();

}
