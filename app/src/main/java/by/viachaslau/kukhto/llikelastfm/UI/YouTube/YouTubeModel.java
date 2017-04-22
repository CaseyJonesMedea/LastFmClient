package by.viachaslau.kukhto.llikelastfm.UI.YouTube;

import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
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
