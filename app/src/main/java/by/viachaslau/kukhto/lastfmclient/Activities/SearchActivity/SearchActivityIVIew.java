package by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity;

import by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity.SearchActivityAdapters.SearchAdapter;

/**
 * Created by kuhto on 15.03.2017.
 */

public interface SearchActivityIVIew {

    void showList(SearchAdapter adapter);

    void showErrorFragment();

    void showNotFoundFragment();

    void showLoadFragment();

    void hideLoadFragment();
}
