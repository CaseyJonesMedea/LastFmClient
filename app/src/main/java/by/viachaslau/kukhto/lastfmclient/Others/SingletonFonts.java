package by.viachaslau.kukhto.lastfmclient.Others;

import android.content.Context;
import android.graphics.Typeface;

import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;

/**
 * Created by kuhto on 29.12.2016.
 */

public class SingletonFonts {

    private static Typeface font1;
    private static Typeface font2;
    private static Typeface font3;

    public Typeface getFont1() {
        return font1;
    }

    public Typeface getFont2() {
        return font2;
    }

    public Typeface getFont3() {
        return font3;
    }


    public static void setFont1(Typeface font1) {
        SingletonFonts.font1 = font1;
    }

    public static void setFont2(Typeface font2) {
        SingletonFonts.font2 = font2;
    }

    public static void setFont3(Typeface font3) {
        SingletonFonts.font3 = font3;
    }

    private static volatile SingletonFonts instance;

    private SingletonFonts() {}

    public static SingletonFonts getInstance(Context activity) {
        SingletonFonts localInstance = instance;
        if (localInstance == null) {
            synchronized (SingletonFonts.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SingletonFonts();
                }
            }
            setFont1(Typeface.createFromAsset(activity.getAssets(), "fonts/28_days_later.ttf"));
            setFont2(Typeface.createFromAsset(activity.getAssets(), "fonts/1979_regular.ttf"));
            setFont3(Typeface.createFromAsset(activity.getAssets(), "fonts/abosanovash.ttf"));

        }
        return localInstance;
    }


}