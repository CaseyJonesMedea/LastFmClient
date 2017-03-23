package by.viachaslau.kukhto.lastfmclient.Activities.UserActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.ChartFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.FriendsFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.HomeFragmentInformation;

import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.User;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonPreference;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonSession;
import by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity.SearchActivity;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments.ChartFragmentUser;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments.FriendsFragmentUser;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments.HomeFragmentUser;
import by.viachaslau.kukhto.lastfmclient.Activities.WelcomeActivity.WelcomeActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public class UserActivityPresenter implements UserActivityIPresenter {

    public static final String TAG = UserActivityPresenter.class.getSimpleName();

    private UserActivityIView iView;
    private Subscription subscription = Subscribers.empty();

    private User user;

    private Map<String, Fragment> fragments = new HashMap<>();
    private String fragmentInActivity;


    public UserActivityPresenter(UserActivityIView iView, Intent intent) {
        AppLog.log(TAG, "createUserActivityPresenter");
        this.iView = iView;
        initializeUserInformation(intent);
    }


    private void initializeUserInformation(Intent intent) {
        AppLog.log(TAG, "initializeUserInformation");
        String userName = intent.getStringExtra(UserActivity.USER_NAME);
        loadUser(userName);
    }

    private void loadUser(String userName) {
        AppLog.log(TAG, "loadUser");
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getUserInfo(userName).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(User user) {
                AppLog.log(TAG, "onNext");
                initUser(user);
                iView.hideLoadProgressBar();
                iView.initUserFull(user);
                initHomeFragment();
            }
        });
    }

    private void initUser(User user) {
        AppLog.log(TAG, "initUser");
        this.user = user;
    }


    private void initHomeFragment() {
        AppLog.log(TAG, "initHomeFragment");
        fragmentInActivity = HomeFragmentUser.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getHomeFragmentInformation(user.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HomeFragmentInformation>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
                SingletonSession.clearSession();
            }

            @Override
            public void onNext(HomeFragmentInformation homeFragmentInformation) {
                AppLog.log(TAG, "onNext");
                HomeFragmentUser homeFragmentUser = HomeFragmentUser.newInstance(homeFragmentInformation, user.getName());
                fragments.put(HomeFragmentUser.TAG, homeFragmentUser);
                iView.showFragment(homeFragmentUser, false, HomeFragmentUser.TAG);
            }
        });
    }

    private void initChartFragment() {
        AppLog.log(TAG, "initChartFragment");
        fragmentInActivity = ChartFragmentUser.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getChartFragmentInformation(user.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ChartFragmentInformation>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
                SingletonSession.clearSession();
            }

            @Override
            public void onNext(ChartFragmentInformation chartFragmentInformation) {
                AppLog.log(TAG, "onNext");
                ChartFragmentUser chartFragmentUser = ChartFragmentUser.newInstance(chartFragmentInformation);
                fragments.put(ChartFragmentUser.TAG, chartFragmentUser);
                iView.showFragment(chartFragmentUser, false, ChartFragmentUser.TAG);
            }
        });
    }

    private void initFriendsFragment() {
        AppLog.log(TAG, "initFriendsFragment");
        fragmentInActivity = FriendsFragmentUser.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getFriendsFragmentInformation(user.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<FriendsFragmentInformation>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
                SingletonSession.clearSession();
            }

            @Override
            public void onNext(FriendsFragmentInformation friendsFragmentInformation) {
                AppLog.log(TAG, "onNext");
                FriendsFragmentUser friendsFragmentUser = FriendsFragmentUser.newInstance(friendsFragmentInformation);
                fragments.put(FriendsFragmentUser.TAG, friendsFragmentUser);
                iView.showFragment(friendsFragmentUser, false, FriendsFragmentUser.TAG);
            }
        });
    }


    @Override
    public void onBtnExitClick() {
        AppLog.log(TAG, "onBtnExitClick");
        SingletonPreference.getInstance().clearUserInformation();
        Intent intent = new Intent(iView.getContext(), WelcomeActivity.class);
        iView.getContext().startActivity(intent);
        ((AppCompatActivity) iView.getContext()).finish();
    }

    @Override
    public void onBtnHomeClick() {
        AppLog.log(TAG, "onBtnHomeClick");
        fragmentInActivity = HomeFragmentUser.TAG;
        HomeFragmentUser fragmentUser = null;
        for (Map.Entry entry : fragments.entrySet()) {
            if (entry.getKey().equals(HomeFragmentUser.TAG)) {
                fragmentUser = (HomeFragmentUser) entry.getValue();
                break;
            }
        }
        if (fragmentUser != null) {
            fragments.put(HomeFragmentUser.TAG, fragmentUser);
            iView.showFragment(fragmentUser, false, HomeFragmentUser.TAG);
        } else {
            initHomeFragment();
        }
    }

    @Override
    public void onBtnFriendsClick() {
        AppLog.log(TAG, "onBtnFriendsClick");
        fragmentInActivity = FriendsFragmentUser.TAG;
        FriendsFragmentUser fragmentUser = null;
        for (Map.Entry entry : fragments.entrySet()) {
            if (entry.getKey().equals(FriendsFragmentUser.TAG)) {
                fragmentUser = (FriendsFragmentUser) entry.getValue();
                break;
            }
        }
        if (fragmentUser != null) {
            fragments.put(FriendsFragmentUser.TAG, fragmentUser);
            iView.showFragment(fragmentUser, false, FriendsFragmentUser.TAG);
        } else {
            initFriendsFragment();
        }
    }

    @Override
    public void onBtnUserChartClick() {
        AppLog.log(TAG, "onBtnUserChartClick");
        fragmentInActivity = ChartFragmentUser.TAG;
        ChartFragmentUser fragmentUser = null;
        for (Map.Entry entry : fragments.entrySet()) {
            if (entry.getKey().equals(ChartFragmentUser.TAG)) {
                fragmentUser = (ChartFragmentUser) entry.getValue();
                break;
            }
        }
        if (fragmentUser != null) {
            fragments.put(ChartFragmentUser.TAG, fragmentUser);
            iView.showFragment(fragmentUser, false, ChartFragmentUser.TAG);
        } else {
            initChartFragment();
        }
    }


    @Override
    public void onBtnUpdateClick() {
        AppLog.log(TAG, "onBtnUpdateClick");
        if (fragmentInActivity.equals(HomeFragmentUser.TAG)) {
            initHomeFragment();
        } else if (fragmentInActivity.equals(FriendsFragmentUser.TAG)) {
            initFriendsFragment();
        } else if (fragmentInActivity.equals(ChartFragmentUser.TAG)) {
            initChartFragment();
        }
    }

    @Override
    public void onBtnSearchClick() {
        AppLog.log(TAG, "onBtnSearchClick");
        Intent intent = new Intent(iView.getContext(), SearchActivity.class);
        iView.getContext().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        iView = null;
        fragmentInActivity = null;
        fragments = null;
        RxUtils.unsubscribe(subscription);
    }
}
