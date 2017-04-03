package by.viachaslau.kukhto.lastfmclient.UI.User.UserActivityFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public class ErrorFragmentUser extends Fragment {

    public static final String TAG = ErrorFragmentUser.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.log(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_user_error, container, false);
        return view;
    }
}
