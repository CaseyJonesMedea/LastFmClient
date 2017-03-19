package by.viachaslau.kukhto.lastfmclient.Activities.UserActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
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

    private Context context;
    private UserActivityIView iView;
    private Subscription subscription = Subscribers.empty();

    private User user;

    private Map<String, Fragment> fragments = new HashMap<>();
    private String fragmentInActivity;


    public UserActivityPresenter(Context context, UserActivityIView iView, Intent intent) {
        this.context = context;
        this.iView = iView;
        initializeUserInformation(intent);
    }


    private void initializeUserInformation(Intent intent) {
        String userName = intent.getStringExtra(UserActivity.USER_NAME);
        loadUser(userName);
    }

    private void loadUser(String userName) {
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getUserInfo(userName).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                iView.hideLoadProgressBar();
            }

            @Override
            public void onNext(User user) {
                initUser(user);
                iView.hideLoadProgressBar();
                iView.initUserFull(user);
                initHomeFragment();
            }
        });
    }

    private void initUser(User user) {
        this.user = user;
    }


    private void initHomeFragment() {
        fragmentInActivity = HomeFragmentUser.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getHomeFragmentInformation(user.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HomeFragmentInformation>() {
            @Override
            public void onCompleted() {
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                SingletonSession.clearSession();
            }

            @Override
            public void onNext(HomeFragmentInformation homeFragmentInformation) {
                HomeFragmentUser homeFragmentUser = HomeFragmentUser.newInstance(homeFragmentInformation, user.getName());
                fragments.put(HomeFragmentUser.TAG, homeFragmentUser);
                iView.showFragment(homeFragmentUser, false, HomeFragmentUser.TAG);
            }
        });
    }

    private void initChartFragment() {
        fragmentInActivity = ChartFragmentUser.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getChartFragmentInformation(user.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ChartFragmentInformation>() {
            @Override
            public void onCompleted() {
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                SingletonSession.clearSession();
            }

            @Override
            public void onNext(ChartFragmentInformation chartFragmentInformation) {
                ChartFragmentUser chartFragmentUser = ChartFragmentUser.newInstance(chartFragmentInformation);
                fragments.put(ChartFragmentUser.TAG, chartFragmentUser);
                iView.showFragment(chartFragmentUser, false, ChartFragmentUser.TAG);
            }
        });
    }

    private void initFriendsFragment() {
        fragmentInActivity = FriendsFragmentUser.TAG;
        iView.showLoadProgressBar();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getFriendsFragmentInformation(user.getName()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<FriendsFragmentInformation>() {
            @Override
            public void onCompleted() {
                iView.hideLoadProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
                SingletonSession.clearSession();
            }

            @Override
            public void onNext(FriendsFragmentInformation friendsFragmentInformation) {
                FriendsFragmentUser friendsFragmentUser = FriendsFragmentUser.newInstance(friendsFragmentInformation);
                fragments.put(FriendsFragmentUser.TAG, friendsFragmentUser);
                iView.showFragment(friendsFragmentUser, false, FriendsFragmentUser.TAG);
            }
        });
    }


    @Override
    public void onBtnExitClick() {
        SingletonPreference.getInstance().clearUserInformation();
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();
    }

    @Override
    public void onBtnHomeClick() {
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
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
