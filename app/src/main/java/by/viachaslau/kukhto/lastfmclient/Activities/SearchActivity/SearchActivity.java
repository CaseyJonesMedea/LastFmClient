package by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity.SearchActivityAdapters.SearchAdapter;


/**
 * Created by kuhto on 15.03.2017.
 */

public class SearchActivity extends AppCompatActivity implements SearchActivityIVIew, View.OnClickListener {

    private EditText edtSearch;
    private RecyclerView listSearch;
    private ImageView btnSearch;
    private ImageView btnClose;

    private View fragmentLoad;

    private View fragmentNotInformation;

    private View fragmentError;

    private RadioButton radioButtonArtist;
    private RadioButton radioButtonAlbum;
    private RadioButton radioButtonTrack;

    private SearchActivityPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        presenter = new SearchActivityPresenter(this, this, edtSearch, radioButtonArtist, radioButtonAlbum, radioButtonTrack);
    }


    private void initViews() {
        edtSearch = (EditText) findViewById(R.id.search_info);
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    presenter.onBtnSearchClick();
                    return true;
                }
                return false;
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtSearch.getText().toString().equals("")) {
                    btnClose.setVisibility(View.INVISIBLE);
                } else {
                    btnClose.setVisibility(View.VISIBLE);
                }
            }
        });
        fragmentLoad = findViewById(R.id.fragment_load);
        fragmentNotInformation = findViewById(R.id.fragment_not_information);
        fragmentError = findViewById(R.id.fragment_error);

        listSearch = (RecyclerView) findViewById(R.id.search_recycler);
        radioButtonArtist = (RadioButton) findViewById(R.id.radio_btn_artist);
        radioButtonAlbum = (RadioButton) findViewById(R.id.radio_btn_album);
        radioButtonTrack = (RadioButton) findViewById(R.id.radio_btn_track);
        btnSearch = (ImageView) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
        btnClose = (ImageView) findViewById(R.id.btn_cancel);
        btnClose.setOnClickListener(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        listSearch.setLayoutManager(linearLayout);
    }


    @Override
    public void showList(SearchAdapter adapter) {
        fragmentError.setVisibility(View.INVISIBLE);
        fragmentNotInformation.setVisibility(View.INVISIBLE);
        fragmentLoad.setVisibility(View.INVISIBLE);
        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(adapter);
    }

    @Override
    public void showErrorFragment() {
        fragmentError.setVisibility(View.VISIBLE);
        fragmentNotInformation.setVisibility(View.INVISIBLE);
        fragmentLoad.setVisibility(View.INVISIBLE);
        listSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNotFoundFragment() {
        fragmentError.setVisibility(View.INVISIBLE);
        fragmentNotInformation.setVisibility(View.VISIBLE);
        fragmentLoad.setVisibility(View.INVISIBLE);
        listSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoadFragment() {
        fragmentError.setVisibility(View.INVISIBLE);
        fragmentNotInformation.setVisibility(View.INVISIBLE);
        fragmentLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadFragment() {
        fragmentError.setVisibility(View.INVISIBLE);
        fragmentNotInformation.setVisibility(View.INVISIBLE);
        fragmentLoad.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                presenter.onBtnSearchClick();
                break;
            case R.id.btn_cancel:
                edtSearch.setText("");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
