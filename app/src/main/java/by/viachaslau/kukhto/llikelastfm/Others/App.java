package by.viachaslau.kukhto.llikelastfm.Others;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;


import by.viachaslau.kukhto.llikelastfm.DI.AppComponent;
import by.viachaslau.kukhto.llikelastfm.DI.DaggerAppComponent;
import by.viachaslau.kukhto.llikelastfm.Others.Model.AppLog;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Caller;
import by.viachaslau.kukhto.llikelastfm.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by CaseyJones on 11.12.2016.
 */

public class App extends Application {

    public static final String TAG = App.class.getSimpleName();

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        AppLog.log(TAG, "onCreate");
        Fabric.with(this, new Crashlytics());
        component = DaggerAppComponent.create();
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.application_admob));
        AppLog.newInstance(true);
        initializeImageLoader(this);
        initializeLastFm();
        SingletonPreference.newInstance(this);
    }

    private void initializeImageLoader(Context context) {
        AppLog.log(TAG, "initializeImageLoader");
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        DisplayImageOptions options =
                new DisplayImageOptions.Builder()
                        .showImageOnFail(R.drawable.user_logo)
                        .showImageOnLoading(R.drawable.user_logo)
                        .cacheInMemory(true).cacheOnDisk(true).build();
        config.defaultDisplayImageOptions(options);
        config.threadPoolSize(5);
        L.writeLogs(false);
        config.diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context), null, imageUri -> {
            String generate = String.valueOf(imageUri.hashCode());
            return generate;
        }));

        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        ImageLoader.getInstance().init(config.build());
    }

    private void initializeLastFm() {
        AppLog.log(TAG, "initializeLastFm");
        Caller.getInstance().setUserAgent(Data.user);
        Caller.getInstance().setDebugMode(true);
        Caller.getInstance().setCache(null);
    }

    public static AppComponent getComponent() {
        return component;
    }


}
