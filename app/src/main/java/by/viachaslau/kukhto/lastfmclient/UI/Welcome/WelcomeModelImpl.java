package by.viachaslau.kukhto.lastfmclient.UI.Welcome;

import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.UserInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Authenticator;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Session;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonPreference;
import rx.Observable;


/**
 * Created by kuhto on 28.03.2017.
 */

public class WelcomeModelImpl extends ModelImpl implements WelcomeModel{


    public WelcomeModelImpl() {
        super();
    }

    @Override
    public Observable getSharedPreferencesUserInfo() {
        Observable<UserInformation> observable = Observable.fromCallable(() -> {
            Thread.sleep(3000);
            UserInformation information = SingletonPreference.getInstance().getUserInfo();
            return information;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getMobileSession(UserInformation information) {
        Observable<Session> observable = Observable.fromCallable(() -> Authenticator.getMobileSession(information.getName(), information.getPassword(), Data.API_KEY, Data.SECRET)).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }
}
