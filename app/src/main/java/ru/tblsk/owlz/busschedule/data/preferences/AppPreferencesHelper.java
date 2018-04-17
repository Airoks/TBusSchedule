package ru.tblsk.owlz.busschedule.data.preferences;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.tblsk.owlz.busschedule.di.application.ApplicationContext;
import ru.tblsk.owlz.busschedule.di.application.PreferencesInfo;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREF_KEY_FIRST_RUN = "PREF_KEY_FIRST_RUN";

    private final SharedPreferences sharedPreferences;

    private Gson gson;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context, @PreferencesInfo String fileName) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        gson = new GsonBuilder().create();
    }

    @Override
    public void setFirstRunVariable(boolean value) {
        sharedPreferences.edit().putBoolean(PREF_KEY_FIRST_RUN, value).apply();

    }

    @Override
    public boolean getFirstRunVariable() {
        return sharedPreferences.getBoolean(PREF_KEY_FIRST_RUN, true);
    }
}
