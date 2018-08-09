package com.example.mcbot.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kimyebon on 2018-08-08.
 */

public class SharedPreferencesManager {
    private static SharedPreferencesManager instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private String NAME = "MCBot";
    private String USERNAME = "username";
    private String PASSWORD = "password";

    private SharedPreferencesManager(Context context) {
        pref = context.getSharedPreferences(NAME, 0);
        editor = pref.edit();
    }

    synchronized public static SharedPreferencesManager getInstance(Context context) {
        if(instance == null)
            instance = new SharedPreferencesManager(context);

        return instance;
    }

    public String getUserName() {
        return pref.getString(USERNAME, "bony");
    }

    public void setUsername(String username) {
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD, password);
        editor.commit();
    }
}
