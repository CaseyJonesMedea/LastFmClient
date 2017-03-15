package by.viachaslau.kukhto.lastfmclient.View.ArtistActivity.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.R;


/**
 * Created by kuhto on 03.03.2017.
 */

public class InfoFragmentArtist extends Fragment {

    public static final String TAG = InfoFragmentArtist.class.getSimpleName();

    private static final String INFO_FRAGMENT_INFORMATION = "infoFragmentInformation";

    private View view;

    private TextView wikiInfo;


    public static InfoFragmentArtist newInstance(Artist artist) {
        InfoFragmentArtist fragmentUser = new InfoFragmentArtist();
        Bundle bundle = new Bundle();
        bundle.putSerializable(INFO_FRAGMENT_INFORMATION, artist);
        fragmentUser.setArguments(bundle);
        return fragmentUser;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_artist_info, container, false);
            initViews(view);
            Artist artist = (Artist) getArguments().getSerializable(INFO_FRAGMENT_INFORMATION);
            wikiInfo.setText(artist.getWikiText());
        }
        return view;
    }

    private void initViews(View view) {
        wikiInfo = (TextView)view.findViewById(R.id.wiki_info);
    }



}
