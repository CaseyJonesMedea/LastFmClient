package by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp;

import java.io.Serializable;
import java.util.List;


import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;

/**
 * Created by kuhto on 23.02.2017.
 */

public class ChartFragmentInformation implements Serializable{


    private List<Track> chartTracks;

    public ChartFragmentInformation(List<Track> chartTracks) {
        this.chartTracks = chartTracks;
    }

    public List<Track> getChartTracks() {
        return chartTracks;
    }

    public void setChartTracks(List<Track> chartTracks) {
        this.chartTracks = chartTracks;
    }
}
