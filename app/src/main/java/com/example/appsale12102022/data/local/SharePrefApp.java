package com.example.appsale12102022.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pphat on 2/3/2023.
 */
public class SharePrefApp {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SharePrefApp instance = null;

    private SharePrefApp(Context context) {
        sharedPreferences = context.getSharedPreferences("share_pref_app", Context.MODE_PRIVATE);
    }

    public static SharePrefApp getInstance(Context context) {
        if (instance == null) {
            instance = new SharePrefApp(context);
        }
        return instance;
    }

    public void saveDataString(String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getDataString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void clearCache() {
        sharedPreferences.edit().clear().commit();
    }
}
