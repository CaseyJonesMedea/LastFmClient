package by.viachaslau.kukhto.llikelastfm.UI.WorldChard;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by kuhto on 07.03.2017.
 */

public interface WorldChartActivityIView {

    void showLoadProgressBar();

    void hideLoadProgressBar();

    void showErrorFragment();

    void showFragment(Fragment fragment, boolean addToBackStack, String tag);

    Context getContext();

}
