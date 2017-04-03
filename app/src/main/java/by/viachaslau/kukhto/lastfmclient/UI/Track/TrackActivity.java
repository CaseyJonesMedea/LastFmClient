package by.viachaslau.kukhto.lastfmclient.UI.Track;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import by.viachaslau.kukhto.lastfmclient.Others.App;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 10.03.2017.
 */

public class TrackActivity extends AppCompatActivity implements TrackActivityIView {

    public static final String TAG = TrackActivity.class.getSimpleName();

    public static final String TRACK = "track";

    @Inject
    protected TrackActivityPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppLog.log(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        App.getComponent().inject(this);
        presenter.onCreate(this, getIntent());
    }

    @Override
    public void closeActivity() {
        AppLog.log(TAG, "closeActivity");
        finish();
    }

    @Override
    public Context getContext(){
        return this;
    }

    @Override
    protected void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        presenter.onDestroy();
        super.onDestroy();
    }
}
