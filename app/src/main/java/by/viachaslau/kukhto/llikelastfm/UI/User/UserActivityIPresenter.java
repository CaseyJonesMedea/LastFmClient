package by.viachaslau.kukhto.llikelastfm.UI.User;

import android.content.Intent;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public interface UserActivityIPresenter {

    void onCreate(UserActivityIView iView, Intent intent);

    void onBtnExitClick();

    void onBtnHomeClick();

    void onBtnFriendsClick();

    void onBtnUserChartClick();

    void onBtnUpdateClick();

    void onBtnSearchClick();

    void onDestroy();

}
