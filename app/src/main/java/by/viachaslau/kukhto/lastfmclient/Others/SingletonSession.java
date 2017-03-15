package by.viachaslau.kukhto.lastfmclient.Others;

import android.content.Context;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Session;


/**
 * Created by CaseyJones on 28.01.2017.
 */

public class SingletonSession {


    private static volatile SingletonSession instance;

    private Session session;

    public Session getSession() {
        return session;
    }

    private SingletonSession(Session session) {
        this.session = session;
    }

    public static void newInstance(Session session) {
        if (instance == null) {
            instance = new SingletonSession(session);
        }
    }


    public static SingletonSession getInstance() {
        return instance;
    }

    public static void clearSession(){
        instance = null;
    }

}
