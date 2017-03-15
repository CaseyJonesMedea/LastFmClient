package by.viachaslau.kukhto.lastfmclient.View.TrackActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 10.03.2017.
 */

public class TrackActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_track);
    }
}
