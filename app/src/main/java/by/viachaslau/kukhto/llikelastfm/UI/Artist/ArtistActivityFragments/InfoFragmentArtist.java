package by.viachaslau.kukhto.llikelastfm.UI.Artist.ArtistActivityFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.llikelastfm.Others.Model.AppLog;
import by.viachaslau.kukhto.llikelastfm.Others.Model.TextUtils;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.llikelastfm.R;


/**
 * Created by kuhto on 03.03.2017.
 */

public class InfoFragmentArtist extends Fragment {

    public static final String TAG = InfoFragmentArtist.class.getSimpleName();

    private static final String INFO_FRAGMENT_INFORMATION = "infoFragmentInformation";

    @BindView(R.id.wiki_info)
    TextView wikiInfo;


    public static InfoFragmentArtist newInstance(Artist artist) {
        AppLog.log(TAG, "newInstance");
        InfoFragmentArtist fragmentUser = new InfoFragmentArtist();
        Bundle bundle = new Bundle();
        bundle.putSerializable(INFO_FRAGMENT_INFORMATION, artist);
        fragmentUser.setArguments(bundle);
        return fragmentUser;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_info, container, false);
        AppLog.log(TAG, "onCreateView");
        ButterKnife.bind(this, view);
        Artist artist = (Artist) getArguments().getSerializable(INFO_FRAGMENT_INFORMATION);
        wikiInfo.setText(TextUtils.html2text(artist.getWikiText()));
        return view;
    }


}
