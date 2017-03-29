package by.viachaslau.kukhto.lastfmclient.UI.TrackActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public class TrackModelImpl extends ModelImpl implements TrackModel {

    public TrackModelImpl() {
        super();
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
        }).subscribeOn(ioThread).observeOn(uiThread);
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
}
