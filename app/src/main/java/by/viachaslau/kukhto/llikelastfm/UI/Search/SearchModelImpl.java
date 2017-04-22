package by.viachaslau.kukhto.llikelastfm.UI.Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.viachaslau.kukhto.llikelastfm.Others.Data;
import by.viachaslau.kukhto.llikelastfm.Others.Model.ModelImpl;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
import rx.Observable;


/**
 * Created by kuhto on 28.03.2017.
 */

public class SearchModelImpl extends ModelImpl implements SearchModel {


    public SearchModelImpl() {
        super();
    }

    @Override
    public Observable getSearchArtist(String name) {
        Observable<List<Artist>> observable = Observable.fromCallable(() -> {
            List<Artist> searchArtists = null;
            try {
                Collection<Artist> collection = Artist.search(name, Data.API_KEY);
                searchArtists = new ArrayList<>(collection);
                return searchArtists;
            } catch (Exception ex) {
                searchArtists = new ArrayList<>();
                return searchArtists;
            }
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getSearchAlbum(String name) {
        Observable<List<Album>> observable = Observable.fromCallable(() -> {
            List<Album> searchAlbums = null;
            try {
                Collection<Album> collection = Album.search(name, Data.API_KEY);
                searchAlbums = new ArrayList<>(collection);
                return searchAlbums;
            } catch (Exception ex) {
                searchAlbums = new ArrayList<>();
                return searchAlbums;
            }
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getSearchTrack(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            try {
                List<Track> searchTracks = findTracks(name);
                return searchTracks;
            } catch (Exception ex) {
                List<Track> searchTracks = new ArrayList<>();
                return searchTracks;
            }
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    private List<Track> findTracks(String name) {
        List<Track> trackList = new ArrayList<>();
        try {
            URL url = new URL(Data.BASE_URL + "?method=track.search&track=" + name + "&api_key=" + Data.API_KEY + "&format=json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String resultJson = buffer.toString();
            JSONObject dataJsonObj = new JSONObject(resultJson);
            JSONObject result = dataJsonObj.getJSONObject("results");
            JSONObject trackMatchers = result.getJSONObject("trackmatches");
            JSONArray searchTracks = trackMatchers.getJSONArray("track");
            for (int i = 0; i < searchTracks.length(); i++) {
                JSONObject track = searchTracks.getJSONObject(i);
                String nameTrack = track.getString("name");
                String artist = track.getString("artist");
                String urlTrack = track.getString("url");
                int listeners = track.getInt("listeners");
                JSONArray image = track.getJSONArray("image");
                Map<ImageSize, String> map = new HashMap<>();
                for (int j = 0; j < image.length(); j++) {
                    JSONObject imageUrlObject = image.getJSONObject(j);
                    String imageUrl = imageUrlObject.getString("#text");
                    String imageSize = imageUrlObject.getString("size");
                    map.put(ConvertStringToImageSize(imageSize), imageUrl);
                }
                Track trackfinal = new Track(nameTrack, urlTrack, artist);
                trackfinal.setImageUrls(map);
                trackfinal.setListeners(listeners);
                trackList.add(trackfinal);
            }
        } catch (IOException | JSONException e) {
            return new ArrayList<>();
        }
        return trackList;
    }

    private ImageSize ConvertStringToImageSize(String name) {
        if (name.equals("small")) {
            return ImageSize.SMALL;
        } else if (name.equals("medium")) {
            return ImageSize.MEDIUM;
        } else if (name.equals("large")) {
            return ImageSize.LARGE;
        } else if (name.equals("extralarge")) {
            return ImageSize.EXTRALARGE;
        } else {
            return null;
        }
    }
}
