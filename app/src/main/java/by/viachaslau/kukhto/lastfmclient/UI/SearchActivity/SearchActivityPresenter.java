package by.viachaslau.kukhto.lastfmclient.UI.SearchActivity;

import android.widget.EditText;
import android.widget.RadioButton;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.UI.SearchActivity.SearchActivityAdapters.AlbumSearchAdapter;
import by.viachaslau.kukhto.lastfmclient.UI.SearchActivity.SearchActivityAdapters.ArtistSearchAdapter;
import by.viachaslau.kukhto.lastfmclient.UI.SearchActivity.SearchActivityAdapters.TrackSearchAdapter;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.Subscribers;

/**
 * Created by kuhto on 24.02.2017.
 */

public class SearchActivityPresenter implements SearchActivityIPresenter {

    public static final String TAG = SearchActivityPresenter.class.getSimpleName();

    private SearchActivityIVIew iView;

    private EditText edtSearch;
    private RadioButton radioButtonArtist;
    private RadioButton radioButtonAlbum;
    private RadioButton radioButtonTrack;

    private Subscription subscription = Subscribers.empty();

    protected SearchModelImpl model;

    public SearchActivityPresenter(SearchActivityIVIew iView, EditText edtSearch, RadioButton radioButtonArtist, RadioButton radioButtonAlbum, RadioButton radioButtonTrack) {
        AppLog.log(TAG, "createSearchActivityPresenter");
        model = new SearchModelImpl();
        this.iView = iView;
        this.edtSearch = edtSearch;
        this.radioButtonArtist = radioButtonArtist;
        this.radioButtonAlbum = radioButtonAlbum;
        this.radioButtonTrack = radioButtonTrack;
    }

    @Override
    public void onBtnSearchClick() {
        AppLog.log(TAG, "onBtnSearchClick");
        if (radioButtonArtist.isChecked()) {
            initArtistsList();
        } else if (radioButtonAlbum.isChecked()) {
            initAlbumsList();
        } else if (radioButtonTrack.isChecked()) {
            initTracksList();
        }
    }



    private void initArtistsList() {
        AppLog.log(TAG, "initArtistsList");
        iView.showLoadFragment();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getSearchArtist(edtSearch.getText().toString()).subscribe(new Subscriber<List<Artist>>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
            }

            @Override
            public void onNext(List<Artist> artists) {
                AppLog.log(TAG, "onNext");
                if (artists.size() != 0) {
                    ArtistSearchAdapter adapter = new ArtistSearchAdapter(iView.getContext(), artists);
                    iView.showList(adapter);
                } else {
                    iView.showNotFoundFragment();
                }
            }
        });
    }

    private void initAlbumsList() {
        AppLog.log(TAG, "initAlbumsList");
        iView.showLoadFragment();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getSearchAlbum(edtSearch.getText().toString()).subscribe(new Subscriber<List<Album>>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
            }

            @Override
            public void onNext(List<Album> albums) {
                AppLog.log(TAG, "onNext");
                if (albums.size() != 0) {
                    AlbumSearchAdapter adapter = new AlbumSearchAdapter(albums, iView.getContext());
                    iView.showList(adapter);
                } else {
                    iView.showNotFoundFragment();
                }
            }
        });
    }

    private void initTracksList() {
        AppLog.log(TAG, "initTracksList");
        iView.showLoadFragment();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getSearchTrack(edtSearch.getText().toString()).subscribe(new Subscriber<List<Track>>() {
            @Override
            public void onCompleted() {
                AppLog.log(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AppLog.log(TAG, "onError");
                iView.showErrorFragment();
            }

            @Override
            public void onNext(List<Track> tracks) {
                AppLog.log(TAG, "onNext");
                if (tracks.size() != 0) {
                    TrackSearchAdapter adapter = new TrackSearchAdapter(iView.getContext(), tracks);
                    iView.showList(adapter);
                } else {
                    iView.showNotFoundFragment();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        AppLog.log(TAG, "onDestroy");
        iView = null;
        edtSearch = null;
        radioButtonArtist = null;
        radioButtonAlbum = null;
        radioButtonTrack = null;
        RxUtils.unsubscribe(subscription);
    }
}
