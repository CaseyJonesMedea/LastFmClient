package by.viachaslau.kukhto.lastfmclient.Others.Model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.ChartFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.FriendsFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.HomeFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.LibraryFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.UserInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Authenticator;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Chart;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.PaginatedResult;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Result;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Session;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.User;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.scrobble.ScrobbleData;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.scrobble.ScrobbleResult;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonPreference;
import by.viachaslau.kukhto.lastfmclient.Others.SingletonSession;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CaseyJones on 11.12.2016.
 */


public class ModelImpl implements Model {

    private final Observable.Transformer schedulersTransformer;


    private static Model model;

    public static void newInstance() {
        if (model == null) {
            model = new ModelImpl();
        }
    }

    public static Model getModel() {
        return model;
    }


    private ModelImpl() {
        schedulersTransformer = o -> ((Observable) o).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
        ;
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
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getFriendsFragmentInformation(String user) {
        Observable<FriendsFragmentInformation> observable = Observable.fromCallable(() -> {
            PaginatedResult<User> resultFriends = User.getFriends(user, Data.API_KEY);
            Collection<User> friendsCollection = resultFriends.getPageResults();
            List<User> friends = new ArrayList<>(friendsCollection);
            return new FriendsFragmentInformation(friends);
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getChartFragmentInformation(String user) {
        Observable<ChartFragmentInformation> observable = Observable.fromCallable(() -> {
            Chart<Track> tracksChart = User.getWeeklyTrackChart(user, Data.API_KEY);
            Collection<Track> tracksCollection = tracksChart.getEntries();
            List<Track> tracks = new ArrayList<>(tracksCollection);
            return new ChartFragmentInformation(tracks);
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getSharedPreferencesUserInfo() {
        Observable<UserInformation> observable = Observable.fromCallable(() -> {
            Thread.sleep(3000);
            UserInformation information = SingletonPreference.getInstance().getUserInfo();
            return information;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getSearchArtist(String name) {
        Observable<List<Artist>> observable = Observable.fromCallable(() -> {
            Collection<Artist> collection = Artist.search(name, Data.API_KEY);
            List<Artist> searchArtists = new ArrayList<>(collection);
            return searchArtists;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getSearchAlbum(String name) {
        Observable<List<Album>> observable = Observable.fromCallable(() -> {
            Collection<Album> collection = Album.search(name, Data.API_KEY);
            List<Album> searchAlbums = new ArrayList<>(collection);
            return searchAlbums;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }


    @Override
    public Observable getRecentTracks(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            PaginatedResult<Track> result = User.getRecentTracks(name, 1, 100, Data.API_KEY);
            Collection<Track> collection = result.getPageResults();
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getTopArtists(String name) {
        Observable<List<Artist>> observable = Observable.fromCallable(() -> {
            Collection<Artist> collection = User.getTopArtists(name, Data.API_KEY);
            List<Artist> artists = new ArrayList<>(collection);
            return artists;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getTopAlbums(String name) {
        Observable<List<Album>> observable = Observable.fromCallable(() -> {
            Collection<Album> collection = User.getTopAlbums(name, Data.API_KEY);
            List<Album> albums = new ArrayList<>(collection);
            return albums;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getTopTracks(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            Collection<Track> collection = User.getTopTracks(name, Data.API_KEY);
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getLovedTracks(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            PaginatedResult<Track> result = User.getLovedTracks(name, Data.API_KEY);
            Collection<Track> collection = result.getPageResults();
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getLovedTracksJson(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            List<Track> searchTracks = findLovedTracks(name);
            return searchTracks;
        }).subscribeOn(Schedulers.newThread());
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

    @Override
    public Observable getSimilarArtists(String name) {
        Observable<List<Artist>> observable = Observable.fromCallable(() -> {
            Collection<Artist> collection = Artist.getSimilar(name, Data.API_KEY);
            List<Artist> artists = new ArrayList<>(collection);
            return artists;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getArtistTopAlbums(String name) {
        Observable<List<Album>> observable = Observable.fromCallable(() -> {
            Collection<Album> collection = Artist.getTopAlbums(name, Data.API_KEY);
            List<Album> albums = new ArrayList<>(collection);
            return albums;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getArtistTopTracks(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            Collection<Track> collection = Artist.getTopTracks(name, Data.API_KEY);
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }


    @Override
    public Observable getSearchTrack(String name) {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            List<Track> searchTracks = findTracks(name);
            return searchTracks;
        }).subscribeOn(Schedulers.newThread());
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


    @Override
    public Observable getMobileSession(UserInformation information) {
        Observable<Session> observable = Observable.fromCallable(() -> Authenticator.getMobileSession(information.getName(), information.getPassword(), Data.API_KEY, Data.SECRET)).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getUserInfo(String nameUser) {
        Observable<User> observable = Observable.fromCallable(() -> {
            User user = User.getInfo(nameUser, Data.API_KEY);
            return user;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getArtistInfo(String nameArtist) {
        Observable<Artist> observable = Observable.fromCallable(() -> {
            Artist artist = Artist.getInfo(nameArtist, Data.API_KEY);
            return artist;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getAlbumInfo(String nameArtist, String nameAlbum) {
        Observable<Album> observable = Observable.fromCallable(() -> {
            Album album = Album.getInfo(nameArtist, nameAlbum, Data.API_KEY);
            return album;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getLibraryFragmentInformation(String artist) {
        Observable<LibraryFragmentInformation> observable = Observable.fromCallable(() -> {
            Collection<Artist> similarArtistCollection = Artist.getSimilar(artist, Data.API_KEY);
            List<Artist> similarArtist = new ArrayList<>(similarArtistCollection);
            Collection<Album> artistAlbumsCollection = Artist.getTopAlbums(artist, Data.API_KEY);
            List<Album> artistAlbums = new ArrayList<>(artistAlbumsCollection);
            Collection<Track> artistTopTracksCollection = Artist.getTopTracks(artist, Data.API_KEY);
            List<Track> artistTopTracks = new ArrayList<>(artistTopTracksCollection);
            return new LibraryFragmentInformation(similarArtist, artistAlbums, artistTopTracks);
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getChartTopArtists() {
        Observable<List<Artist>> observable = Observable.fromCallable(() -> {
            PaginatedResult<Artist> result = Chart.getTopArtists(Data.API_KEY);
            Collection<Artist> collection = result.getPageResults();
            List<Artist> artists = new ArrayList<>(collection);
            return artists;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getChartTopTracks() {
        Observable<List<Track>> observable = Observable.fromCallable(() -> {
            PaginatedResult<Track> result = Chart.getTopTracks(Data.API_KEY);
            Collection<Track> collection = result.getPageResults();
            List<Track> tracks = new ArrayList<>(collection);
            return tracks;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getYouTubeUrl(String urlTrack) {
        Observable<String> observable = Observable.fromCallable(() -> {
            Document doc;
            String url = "";
            String code = null;
            try {
                doc = Jsoup.connect(urlTrack).get();
                Elements links = doc.select("a.image-overlay-playlink-link");
                for (Element element : links) {
                    String baseUrl = element.attr("href");
                    if (baseUrl.contains("https://www.youtube.com")) {
                        url = baseUrl;
                    }
                }

                code = extractVideoId(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return code;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getResultLoveTrack(Track track) {
        Observable<Result> observable = Observable.fromCallable(() -> {
            Result result = Track.love(track.getArtist(), track.getName(), SingletonSession.getInstance().getSession());
            return result;
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    @Override
    public Observable getResultUnLoveTrack(Track track) {
        Observable<Result> observable = Observable.fromCallable(() -> {
            Result result = Track.unlove(track.getArtist(), track.getName(), SingletonSession.getInstance().getSession());
            return result;
        }).subscribeOn(Schedulers.newThread());
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
        }).subscribeOn(Schedulers.newThread());
        return observable;
    }

    private String extractVideoId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*");
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }


    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }
}
