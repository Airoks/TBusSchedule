package ru.tblsk.owlz.busschedule.data.preferences;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import ru.tblsk.owlz.busschedule.di.annotation.ApplicationContext;
import ru.tblsk.owlz.busschedule.di.annotation.PreferencesInfo;

public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREF_KEY_FAVORITE = "PREF_KEY_FAVORITE";

    private final SharedPreferences sharedPreferences;

    private Gson gson;

    @Inject
    AppPreferencesHelper(@ApplicationContext Context context, @PreferencesInfo String fileName) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        gson = new GsonBuilder().create();
    }

    private boolean isEmptyFavorite() {
        String favorites = sharedPreferences.getString(PREF_KEY_FAVORITE, "");
        //key not exist or map is empty
        return favorites.isEmpty();
    }



}
