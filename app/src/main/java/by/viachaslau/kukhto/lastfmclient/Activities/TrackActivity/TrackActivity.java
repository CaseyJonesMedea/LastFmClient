package by.viachaslau.kukhto.lastfmclient.Activities.TrackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 10.03.2017.
 */

public class TrackActivity extends AppCompatActivity implements TrackActivityIView {

    public static final String TRACK_URL = "trackUrs";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Intent intent = getIntent();
        new TrackActivityPresenter(this, this, intent);
    }

    @Override
    public void closeActivity() {
        finish();
    }
}
