package by.viachaslau.kukhto.llikelastfm.UI.Search;

import android.content.Context;

import by.viachaslau.kukhto.llikelastfm.UI.Search.SearchActivityAdapters.SearchAdapter;

/**
 * Created by kuhto on 15.03.2017.
 */

public interface SearchActivityIVIew {

    void showList(SearchAdapter adapter);

    void showErrorFragment();

    void showNotFoundFragment();

    void showLoadFragment();

    void hideLoadFragment();

    Context getContext();
}
