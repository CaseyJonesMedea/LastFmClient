package by.viachaslau.kukhto.llikelastfm.UI.YouTube;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


import by.viachaslau.kukhto.llikelastfm.Others.Data;
import by.viachaslau.kukhto.llikelastfm.Others.Model.ModelImpl;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Result;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.scrobble.ScrobbleData;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.scrobble.ScrobbleResult;
import by.viachaslau.kukhto.llikelastfm.Others.SingletonSession;
import rx.Observable;


/**
 * Created by kuhto on 28.03.2017.
 */

public class YouTubeModelImpl extends ModelImpl implements YouTubeModel {

    public YouTubeModelImpl() {
        super();
    }

    @Override
    public Observable getLovedTracksJson(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            List<Track> searchTracks = findLovedTracks(name);
            return searchTracks;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    private List<Track> findLovedTracks(String name) {
        List<Track> trackList = new ArrayList<>();
        try {
            URL url = new URL(Data.BASE_URL + "?method=user.getlovedtracks&user=" + name + "&api_key=" + Data.API_KEY + "&format=json");
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
            JSONObject result = dataJsonObj.getJSONObject("lovedtracks");
            JSONArray lovedTracks = result.getJSONArray("track");

            for (int i = 0; i < lovedTracks.length(); i++) {
                JSONObject track = lovedTracks.getJSONObject(i);
                String nameTrack = track.getString("name");
                String artist = track.getString("mbid");
                String urlTrack = track.getString("url");
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

    @Override
    public Observable getResultLoveTrack(Track track) {
        Observable<Result> observable = Observable.fromCallable(() -> {
            Result result = Track.love(track.getArtist(), track.getName(), SingletonSession.getInstance().getSession());
            return result;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getResultUnLoveTrack(Track track) {
        Observable<Result> observable = Observable.fromCallable(() -> {
            Result result = Track.unlove(track.getArtist(), track.getName(), SingletonSession.getInstance().getSession());
            return result;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getResultTrackScrobble(Track track) {
        Observable<ScrobbleResult> observable = Observable.fromCallable(new Callable<ScrobbleResult>() {
            @Override
            public ScrobbleResult call() throws Exception {
                Calendar date = Calendar.getInstance();
                long milisec = date.getTimeInMillis();
                int sec = (int) (milisec / 1000);
                ScrobbleData data = new ScrobbleData(track.getArtist(), track.getName(), sec);
                ScrobbleResult result = Track.scrobble(data, SingletonSession.getInstance().getSession());
                return result;
            }
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }
}
