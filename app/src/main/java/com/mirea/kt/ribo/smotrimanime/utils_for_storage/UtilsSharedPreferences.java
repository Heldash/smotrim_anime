package com.mirea.kt.ribo.smotrimanime.utils_for_storage;

import android.content.SharedPreferences;

public class UtilsSharedPreferences {
    private SharedPreferences preferences;
    private Account acc;

    public UtilsSharedPreferences(SharedPreferences preferences, Account acc) {
        this.preferences = preferences;
        this.acc = acc;
    }

    public void putPrefUID(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("UID",acc.getUID());
        edit.apply();
    }
    public void putMail(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("mail",acc.getMail());
        edit.apply();
    }
    public void putUserName(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("username", acc.getUserName());
        edit.apply();
    }
}
