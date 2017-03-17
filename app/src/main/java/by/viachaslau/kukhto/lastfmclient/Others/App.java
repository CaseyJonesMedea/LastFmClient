package by.viachaslau.kukhto.lastfmclient.Others;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;


import by.viachaslau.kukhto.lastfmclient.Others.Model.ModelImpl;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Caller;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by CaseyJones on 11.12.2016.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initializeImageLoader(this);
        initializeLastFm();
        SingletonPreference.newInstance(this);
        ModelImpl.newInstance();
    }

    private void initializeImageLoader(Context context) {
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
        Caller.getInstance().setUserAgent(Data.user);
        Caller.getInstance().setDebugMode(true);
        Caller.getInstance().setCache(null);
    }

}
