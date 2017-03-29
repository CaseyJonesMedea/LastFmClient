package by.viachaslau.kukhto.lastfmclient.UI.YouTubeActivity;

import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import rx.Observable;

/**
 * Created by kuhto on 28.03.2017.
 */

public interface YouTubeModel {

    Observable getLovedTracksJson(String name);

    Observable getResultLoveTrack(Track track);

    Observable getResultUnLoveTrack(Track track);

    Observable getResultTrackScrobble(Track track);
}
