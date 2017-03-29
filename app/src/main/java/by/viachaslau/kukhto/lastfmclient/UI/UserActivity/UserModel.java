package by.viachaslau.kukhto.lastfmclient.UI.UserActivity;

import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public interface UserModel {

    Observable getUserInfo(String nameUser);

    Observable getHomeFragmentInformation(String user);

    Observable getChartFragmentInformation(String user);

    Observable getFriendsFragmentInformation(String user);


}
