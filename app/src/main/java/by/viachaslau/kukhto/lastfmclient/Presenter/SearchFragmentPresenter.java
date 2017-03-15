package by.viachaslau.kukhto.lastfmclient.Presenter;

import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.Adapters.AlbumSearchAdapter;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.Adapters.ArtistSearchAdapter;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.Adapters.TrackSearchAdapter;
import by.viachaslau.kukhto.lastfmclient.View.UserActivity.Fragments.SearchFragmentIView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;

/**
 * Created by kuhto on 24.02.2017.
 */

public class SearchFragmentPresenter implements SearchFragmentIPresenter {

    public static final String TAG = SearchFragmentPresenter.class.getSimpleName();

    private SearchFragmentIView iView;

    private EditText edtSearch;
    private RadioButton radioButtonArtist;
    private RadioButton radioButtonAlbum;
    private RadioButton radioButtonTrack;

    private Subscription subscription = Subscribers.empty();

    public SearchFragmentPresenter(SearchFragmentIView iView, EditText edtSearch, RadioButton radioButtonArtist, RadioButton radioButtonAlbum, RadioButton radioButtonTrack) {
        this.iView = iView;
        this.edtSearch = edtSearch;
        this.radioButtonArtist = radioButtonArtist;
        this.radioButtonAlbum = radioButtonAlbum;
        this.radioButtonTrack = radioButtonTrack;
    }

    @Override
    public void onBtnSearchClick() {
        if (radioButtonArtist.isChecked()) {
            initArtistsList();
        } else if (radioButtonAlbum.isChecked()) {
            initAlbumsList();
        } else if (radioButtonTrack.isChecked()) {
            initTracksList();
        }
    }

    private void initArtistsList() {
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getSearchArtist(edtSearch.getText().toString()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Artist>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Artist> artists) {
                ArtistSearchAdapter adapter = new ArtistSearchAdapter(artists);
                iView.showList(adapter);
            }
        });
    }

    private void initAlbumsList() {
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getSearchAlbum(edtSearch.getText().toString()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Album>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Album> albums) {
                AlbumSearchAdapter adapter = new AlbumSearchAdapter(albums);
                iView.showList(adapter);
            }
        });
    }

    private void initTracksList() {
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getSearchTrack(edtSearch.getText().toString()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Track>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.toString());
            }

            @Override
            public void onNext(List<Track> tracks) {
                TrackSearchAdapter adapter = new TrackSearchAdapter(tracks);
                iView.showList(adapter);
            }
        });
    }


}
