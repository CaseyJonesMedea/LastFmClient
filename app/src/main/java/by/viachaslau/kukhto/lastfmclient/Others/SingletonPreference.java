package by.viachaslau.kukhto.lastfmclient.Others;

import android.content.Context;
import android.content.SharedPreferences;

import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.UserInformation;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kuhto on 29.12.2016.
 */

public class SingletonPreference {

    private final String SAVED_NAME = "saved_name";
    private final String SAVED_PASSWORD = "saved_password";
    private final String PREFERENCE = "preference";

    private static volatile SingletonPreference instance;

    private SharedPreferences preferences;

    private Context context;

    private SingletonPreference(Context context) {
        this.context = context;
    }

    public static void newInstance(Context context) {
        if (instance == null) {
            instance = new SingletonPreference(context);
        }
    }


    public static SingletonPreference getInstance() {
        return instance;
    }


    public void saveUserInfo(UserInformation information) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                preferences = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SAVED_NAME, information.getName());
                editor.putString(SAVED_PASSWORD, information.getPassword());
                editor.apply();
            }
        });
        thread.start();
    }

    public UserInformation getUserInfo() {
        preferences = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String name = preferences.getString(SAVED_NAME, "");
        String password = preferences.getString(SAVED_PASSWORD, "");
        if (name.equals("") && password.equals("")) {
            return null;
        }
        return new UserInformation(name, password);
    }

    public void clearUserInformation() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                preferences = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
            }
        });
        thread.start();
    }

}
