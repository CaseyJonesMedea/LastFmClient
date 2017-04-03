package by.viachaslau.kukhto.lastfmclient.Others.Model;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kuhto on 28.03.2017.
 */

public abstract class ModelImpl {

    protected
    Scheduler uiThread;

    protected
    Scheduler ioThread;

    public ModelImpl() {
        uiThread = AndroidSchedulers.mainThread();
        ioThread = Schedulers.io();
    }
}
