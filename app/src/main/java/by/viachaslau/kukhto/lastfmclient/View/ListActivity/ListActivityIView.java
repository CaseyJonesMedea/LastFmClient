package by.viachaslau.kukhto.lastfmclient.View.ListActivity;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;


/**
 * Created by VKukh on 04.03.2017.
 */

public interface ListActivityIView {

    void showLoadProgressBar();

    void hideLoadProgressBar();

    void showErrorFragment();

    void showFragment(Fragment fragment, boolean addToBackStack, String tag);

}
