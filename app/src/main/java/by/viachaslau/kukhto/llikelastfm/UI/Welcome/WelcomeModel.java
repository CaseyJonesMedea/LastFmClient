package by.viachaslau.kukhto.llikelastfm.UI.Welcome;

import by.viachaslau.kukhto.llikelastfm.Others.Model.modelApp.UserInformation;
import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public interface WelcomeModel {

    Observable getSharedPreferencesUserInfo();

    Observable getMobileSession(UserInformation information);

}
