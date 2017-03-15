package by.viachaslau.kukhto.lastfmclient.View.UserActivity.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public class ErrorFragmentUser extends Fragment {

    public static final String TAG = ErrorFragmentUser.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_error, container, false);
        return view;
    }
}
