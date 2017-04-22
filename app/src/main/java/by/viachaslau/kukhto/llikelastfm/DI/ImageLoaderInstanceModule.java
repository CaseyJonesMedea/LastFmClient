package by.viachaslau.kukhto.llikelastfm.DI;
import com.nostra13.universalimageloader.core.ImageLoader;
import by.viachaslau.kukhto.llikelastfm.Others.Model.AppLog;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kuhto on 30.03.2017.
 */


@Module
public class ImageLoaderInstanceModule {

    public static final String TAG = ImageLoaderInstanceModule.class.getSimpleName();

    @Provides
    ImageLoader provideImageLoader(){
        AppLog.log(TAG, "provideImageLoader");
        return ImageLoader.getInstance();
    }
}
