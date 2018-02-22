package ru.tblsk.owlz.busschedule.data.preferences;


import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface PreferencesHelper {
    Completable setFavoriteStops(long stopId, List<Long> directions);
    Observable<Map<Long, List<Long>>> getFavoriteStops();
}
