package com.botmasterzzz.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class UserUtil {

    public static Boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getBoolean("isUserLoggedIn", false);
    }

    public static void setUserLoggedIn(Context context, Boolean isLoggedIn) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isUserLoggedIn", isLoggedIn);
        editor.commit();
    }

    public static void logout(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isUserLoggedIn", false);
        editor.commit();
    }

    public static void saveUsernameAndPassword(Context context, String username, String password) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.commit();
    }

}
