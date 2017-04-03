package by.viachaslau.kukhto.lastfmclient.UI.SearchActivity;

import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by kuhto on 24.02.2017.
 */

public interface SearchActivityIPresenter {

    void onCreate(SearchActivityIVIew iView, EditText edtSearch, RadioButton radioButtonArtist, RadioButton radioButtonAlbum, RadioButton radioButtonTrack);

    void onBtnSearchClick();

    void onDestroy();

}
