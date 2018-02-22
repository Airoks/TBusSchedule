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

    @Override
    public Completable setFavoriteStops(final long stopId, final List<Long> directions) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                @SuppressLint("UseSparseArrays") Map<Long, List<Long>> newFavorites = new HashMap<>();
                newFavorites.put(stopId, directions);
                if(isEmptyFavorite()) {
                    sharedPreferences.edit().remove(PREF_KEY_FAVORITE).apply();
                    Type type = new TypeToken<Map<Long, List<Long>>>(){}.getType();
                    String str = gson.toJson(newFavorites, type);
                    sharedPreferences.edit().putString(PREF_KEY_FAVORITE, str).apply();
                } else {
                    String str = sharedPreferences.getString(PREF_KEY_FAVORITE, "");
                    Type type = new TypeToken<Map<Long, List<Long>>>(){}.getType();
                    Map<Long, List<Long>> favorites = gson.fromJson(str, type);
                    favorites.putAll(newFavorites);
                    sharedPreferences.edit().putString(PREF_KEY_FAVORITE,
                            gson.toJson(favorites, type)).apply();
                }

            }
        });

    }

    @Override
    public Observable<Map<Long, List<Long>>> getFavoriteStops() {
        return Observable.fromCallable(new Callable<Map<Long, List<Long>>>() {
            @Override
            public Map<Long, List<Long>> call() throws Exception {
                String favorites = sharedPreferences.getString(PREF_KEY_FAVORITE, "");
                Type type = new TypeToken<Map<Long, List<Long>>>(){}.getType();
                return gson.fromJson(favorites, type);
            }
        });
    }

}
