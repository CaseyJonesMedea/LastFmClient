package by.viachaslau.kukhto.llikelastfm.UI.List;

import android.content.Intent;

/**
 * Created by VKukh on 04.03.2017.
 */

public interface ListActivityIPresenter {

    void onCreate(ListActivityIView iView, Intent intent);

    void onBtnUpdateClick();

    void onDestroy();

}
