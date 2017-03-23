package by.viachaslau.kukhto.lastfmclient.Others.Model;


import android.util.Log;

/**
 * Created by kuhto on 23.03.2017.
 */

public class AppLog {

    private static volatile AppLog instance;

    private static boolean makeLog;

    public AppLog(boolean makeLog) {
        this.makeLog = makeLog;
    }

    public static void newInstance(boolean makeLog) {
        if (instance == null) {
            instance = new AppLog(makeLog);
        }
    }

    public static void log(String tag, String log) {
        if (makeLog) {
            Log.d(tag, log);
        }
    }

}
