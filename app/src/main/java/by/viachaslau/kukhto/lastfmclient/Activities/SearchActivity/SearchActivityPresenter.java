package by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.RxUtils;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity.SearchActivityAdapters.AlbumSearchAdapter;
import by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity.SearchActivityAdapters.ArtistSearchAdapter;
import by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity.SearchActivityAdapters.TrackSearchAdapter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;

/**
 * Created by kuhto on 24.02.2017.
 */

public class SearchActivityPresenter implements SearchActivityIPresenter {

    public static final String TAG = SearchActivityPresenter.class.getSimpleName();

    private SearchActivityIVIew iView;

    private Context context;

    private EditText edtSearch;
    private RadioButton radioButtonArtist;
    private RadioButton radioButtonAlbum;
    private RadioButton radioButtonTrack;

    private Subscription subscription = Subscribers.empty();

    public SearchActivityPresenter(Context context, SearchActivityIVIew iView, EditText edtSearch, RadioButton radioButtonArtist, RadioButton radioButtonAlbum, RadioButton radioButtonTrack) {
        this.context = context;
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
        iView.showLoadFragment();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getSearchArtist(edtSearch.getText().toString()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Artist>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
            }

            @Override
            public void onNext(List<Artist> artists) {
                if (artists.size() != 0) {
                    ArtistSearchAdapter adapter = new ArtistSearchAdapter(context, artists);
                    iView.showList(adapter);
                } else {
                    iView.showNotFoundFragment();
                }
            }
        });
    }

    private void initAlbumsList() {
        iView.showLoadFragment();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getSearchAlbum(edtSearch.getText().toString()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Album>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
            }

            @Override
            public void onNext(List<Album> albums) {
                if (albums.size() != 0) {
                    AlbumSearchAdapter adapter = new AlbumSearchAdapter(albums, context);
                    iView.showList(adapter);
                } else {
                    iView.showNotFoundFragment();
                }
            }
        });
    }

    private void initTracksList() {
        iView.showLoadFragment();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = ModelImpl.getModel().getSearchTrack(edtSearch.getText().toString()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Track>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                iView.showErrorFragment();
            }

            @Override
            public void onNext(List<Track> tracks) {
                if (tracks.size() != 0) {
                    TrackSearchAdapter adapter = new TrackSearchAdapter(context, tracks);
                    iView.showList(adapter);
                } else {
                    iView.showNotFoundFragment();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        context = null;
        iView = null;
        edtSearch = null;
        radioButtonArtist = null;
        radioButtonAlbum = null;
        radioButtonTrack = null;
        RxUtils.unsubscribe(subscription);
    }
}
