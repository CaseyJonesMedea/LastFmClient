package by.viachaslau.kukhto.lastfmclient.UI.SearchActivity;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.SearchActivity.SearchActivityAdapters.SearchAdapter;


/**
 * Created by kuhto on 15.03.2017.
 */

public class SearchActivity extends AppCompatActivity implements SearchActivityIVIew, View.OnClickListener {

    public static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.search_info)
    EditText edtSearch;
    @BindView(R.id.search_recycler)
    RecyclerView listSearch;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.btn_cancel)
    ImageView btnClose;
    @BindView(R.id.fragment_load)
    View fragmentLoad;
    @BindView(R.id.fragment_not_information)
    View fragmentNotInformation;
    @BindView(R.id.fragment_error)
    View fragmentError;
    @BindView(R.id.radio_btn_artist)
    RadioButton radioButtonArtist;
    @BindView(R.id.radio_btn_album)
    RadioButton radioButtonAlbum;
    @BindView(R.id.radio_btn_track)
    RadioButton radioButtonTrack;

    private SearchActivityPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        initInitialize();
        presenter = new SearchActivityPresenter(this, edtSearch, radioButtonArtist, radioButtonAlbum, radioButtonTrack);
    }


    private void initInitialize() {
        AppLog.log(TAG, "onCreate");
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                AppLog.log(TAG, "onKey");
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
                AppLog.log(TAG, "afterTextChanged");
                if (edtSearch.getText().toString().equals("")) {
                    btnClose.setVisibility(View.INVISIBLE);
                } else {
                    btnClose.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSearch.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        listSearch.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void showList(SearchAdapter adapter) {
        AppLog.log(TAG, "showList");
        fragmentError.setVisibility(View.INVISIBLE);
        fragmentNotInformation.setVisibility(View.INVISIBLE);
        fragmentLoad.setVisibility(View.INVISIBLE);
        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(adapter);
    }

    @Override
    public void showErrorFragment() {
        AppLog.log(TAG, "showErrorFragment");
        fragmentError.setVisibility(View.VISIBLE);
        fragmentNotInformation.setVisibility(View.INVISIBLE);
        fragmentLoad.setVisibility(View.INVISIBLE);
        listSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNotFoundFragment() {
        AppLog.log(TAG, "showNotFoundFragment");
        fragmentError.setVisibility(View.INVISIBLE);
        fragmentNotInformation.setVisibility(View.VISIBLE);
        fragmentLoad.setVisibility(View.INVISIBLE);
        listSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoadFragment() {
        AppLog.log(TAG, "showLoadFragment");
        fragmentError.setVisibility(View.INVISIBLE);
        fragmentNotInformation.setVisibility(View.INVISIBLE);
        fragmentLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadFragment() {
        AppLog.log(TAG, "hideLoadFragment");
        fragmentError.setVisibility(View.INVISIBLE);
        fragmentNotInformation.setVisibility(View.INVISIBLE);
        fragmentLoad.setVisibility(View.INVISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        AppLog.log(TAG, "onClick");
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
        AppLog.log(TAG, "onDestroy");
        presenter.onDestroy();
        super.onDestroy();
    }
}
