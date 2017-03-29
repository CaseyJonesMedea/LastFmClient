package by.viachaslau.kukhto.lastfmclient.UI.WelcomeActivity;

import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.UserInformation;
import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public interface WelcomeModel {

    Observable getSharedPreferencesUserInfo();

    Observable getMobileSession(UserInformation information);

}
