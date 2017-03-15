package by.viachaslau.kukhto.lastfmclient.View.UserActivity.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Presenter.SearchFragmentIPresenter;
import by.viachaslau.kukhto.lastfmclient.Presenter.SearchFragmentPresenter;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.Adapters.SearchAdapter;

/**
 * Created by kuhto on 24.02.2017.
 */

public class SearchFragmentUser extends Fragment implements SearchFragmentIView, View.OnClickListener {

    private EditText edtSearch;
    private RecyclerView listSearch;
    private Button btnSearch;

    private RadioButton radioButtonArtist;
    private RadioButton radioButtonAlbum;
    private RadioButton radioButtonTrack;

    SearchFragmentPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        initView(view);
        presenter = new SearchFragmentPresenter(this, edtSearch, radioButtonArtist, radioButtonAlbum, radioButtonTrack);
        return view;
    }

    private void initView(View view) {
        edtSearch = (EditText) view.findViewById(R.id.search_info);
        listSearch = (RecyclerView) view.findViewById(R.id.search_recycler);
        radioButtonArtist = (RadioButton) view.findViewById(R.id.radio_btn_artist);
        radioButtonAlbum = (RadioButton) view.findViewById(R.id.radio_btn_album);
        radioButtonTrack = (RadioButton) view.findViewById(R.id.radio_btn_track);
        btnSearch = (Button) view.findViewById(R.id.btn_search_click);
        btnSearch.setOnClickListener(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        listSearch.setLayoutManager(linearLayout);
    }


    @Override
    public void showList(SearchAdapter adapter) {
        listSearch.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search_click:
                presenter.onBtnSearchClick();
                break;
        }
    }
}
