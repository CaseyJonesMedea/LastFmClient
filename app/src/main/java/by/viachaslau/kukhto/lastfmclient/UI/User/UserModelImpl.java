package by.viachaslau.kukhto.lastfmclient.UI.User;

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

import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.ChartFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.FriendsFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.HomeFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Chart;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.PaginatedResult;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.User;
import rx.Observable;


/**
 * Created by kuhto on 28.03.2017.
 */

public class UserModelImpl extends ModelImpl implements UserModel {

    public UserModelImpl() {
        super();
    }

    @Override
    public Observable getUserInfo(String nameUser) {
        Observable<User> observable = Observable.fromCallable(() -> {
            User user = User.getInfo(nameUser, Data.API_KEY);
            return user;
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getHomeFragmentInformation(String user) {
        Observable<HomeFragmentInformation> observable = Observable.fromCallable(() -> {
            PaginatedResult<Track> result = User.getRecentTracks(user, Data.API_KEY);
            Collection<Track> artists = result.getPageResults();
            List<Track> recentTracks = new ArrayList<>(artists);
            Collection<Artist> resultTopArtists = User.getTopArtists(user, Data.API_KEY);
            List<Artist> topArtists = new ArrayList<>(resultTopArtists);
            Collection<Album> resultTopAlbums = User.getTopAlbums(user, Data.API_KEY);
            List<Album> topAlbums = new ArrayList<>(resultTopAlbums);
            Collection<Track> resultTopTracks = User.getTopTracks(user, Data.API_KEY);
            List<Track> topTracks = new ArrayList<>(resultTopTracks);
            PaginatedResult<Track> resultLovedTracks = User.getLovedTracks(user, Data.API_KEY);
            Collection<Track> userLovedTracks = resultLovedTracks.getPageResults();
            List<Track> lovedTracks = new ArrayList<>(userLovedTracks);
            return new HomeFragmentInformation(recentTracks, topArtists, topAlbums, topTracks, lovedTracks);
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getChartFragmentInformation(String user) {
        Observable<ChartFragmentInformation> observable = Observable.fromCallable(() -> {
            Chart<Track> tracksChart = User.getWeeklyTrackChart(user, Data.API_KEY);
            Collection<Track> tracksCollection = tracksChart.getEntries();
            List<Track> tracks = new ArrayList<>(tracksCollection);
            return new ChartFragmentInformation(tracks);
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    @Override
    public Observable getFriendsFragmentInformation(String user) {
        Observable<FriendsFragmentInformation> observable = Observable.fromCallable(() -> {
            try {
                List<User> friends = findFriends(user);
                return new FriendsFragmentInformation(friends);
            } catch (Exception ex) {
                List<User> friends = new ArrayList<>();
                return new FriendsFragmentInformation(friends);
            }
        }).subscribeOn(ioThread).observeOn(uiThread);
        return observable;
    }

    private List<User> findFriends(String user) {
        List<User> userList = new ArrayList<>();
        try {
            URL url = new URL(Data.BASE_URL + "?method=user.getfriends&user=" + user + "&api_key=" + Data.API_KEY + "&format=json");
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
            JSONObject result = dataJsonObj.getJSONObject("friends");
            JSONArray usersJsonObj = result.getJSONArray("user");
            for (int i = 0; i < usersJsonObj.length(); i++) {
                JSONObject userJson = usersJsonObj.getJSONObject(i);
                String nameUser = userJson.getString("name");
                int playCount = userJson.getInt("playcount");
                JSONArray image = userJson.getJSONArray("image");
                Map<ImageSize, String> map = new HashMap<>();
                for (int j = 0; j < image.length(); j++) {
                    JSONObject imageUrlObject = image.getJSONObject(j);
                    String imageUrl = imageUrlObject.getString("#text");
                    String imageSize = imageUrlObject.getString("size");
                    map.put(ConvertStringToImageSize(imageSize), imageUrl);
                }
                User userFinal = new User(nameUser, playCount);
                userFinal.setImageUrls(map);
                userList.add(userFinal);
            }
        } catch (IOException | JSONException e) {
            return new ArrayList<>();
        }
        return userList;
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
