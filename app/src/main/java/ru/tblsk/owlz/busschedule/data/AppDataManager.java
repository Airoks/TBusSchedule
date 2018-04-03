package ru.tblsk.owlz.busschedule.data;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.db.DbHelper;
import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.data.db.model.StopsOnRouts;
import ru.tblsk.owlz.busschedule.data.preferences.PreferencesHelper;
import ru.tblsk.owlz.busschedule.di.annotation.ApplicationContext;
import ru.tblsk.owlz.busschedule.utils.AppConstants;
import ru.tblsk.owlz.busschedule.utils.CommonUtils;

@Singleton
public class AppDataManager implements DataManager {
    private final Context mContext;
    private final DbHelper dbHelper;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper) {
        this.mContext = context;
        this.dbHelper = dbHelper;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public Completable saveFlightList(List<Flight> flightList) {
        return dbHelper.saveFlightList(flightList);
    }

    @Override
    public Completable saveDirectionList(List<Direction> directionList) {
        return dbHelper.saveDirectionList(directionList);
    }

    @Override
    public Completable saveStopsOnRoutsList(List<StopsOnRouts> stopsOnRoutsList) {
        return dbHelper.saveStopsOnRoutsList(stopsOnRoutsList);
    }

    @Override
    public Completable saveStopList(List<Stop> stopList) {
        return dbHelper.saveStopList(stopList);
    }

    @Override
    public Completable saveScheduleList(List<Schedule> scheduleList) {
        return dbHelper.saveScheduleList(scheduleList);
    }

    @Override
    public Completable saveDepartureTimeList(List<DepartureTime> departureTimes) {
        return dbHelper.saveDepartureTimeList(departureTimes);
    }

    @Override
    public Single<Boolean> isEmptyDirection() {
        return dbHelper.isEmptyDirection();
    }

    @Override
    public Single<Boolean> isEmptyFlight() {
        return dbHelper.isEmptyFlight();
    }

    @Override
    public Single<Boolean> isEmptySchedule() {
        return dbHelper.isEmptySchedule();
    }

    @Override
    public Single<Boolean> isEmptyStop() {
        return dbHelper.isEmptyStop();
    }

    @Override
    public Single<Boolean> isEmptyStopsOnRouts() {
        return dbHelper.isEmptyStopsOnRouts();
    }

    @Override
    public Single<Boolean> isEmptyDepartureTime() {
        return dbHelper.isEmptyDepartureTime();
    }

    @Override
    public Single<List<Stop>> getAllStops() {
        return dbHelper.getAllStops()
                .flatMap(new Function<List<Stop>, SingleSource<? extends List<Stop>>>() {
                    @Override
                    public SingleSource<? extends List<Stop>> apply(List<Stop> stops)
                            throws Exception {
                        //сортируем по русскому алфавиту
                        final Collator russianCollator = Collator
                                .getInstance(new Locale("ru", "RU"));
                        Collections.sort(stops, new Comparator<Stop>() {
                            @Override
                            public int compare(Stop stopLeft, Stop stopRight) {
                                return russianCollator.compare(stopLeft.getStopName()
                                        , stopRight.getStopName());
                            }
                        });
                        return Single.just(stops);
                    }
                });
    }

    @Override
    public Single<List<Flight>> getFlightByType(FlightType flightType) {
        return dbHelper.getFlightByType(flightType);
    }

    @Override
    public Single<List<Direction>> getDirectionsByStop(long stopId) {
        return dbHelper.getDirectionsByStop(stopId);
    }

    @Override
    public Single<List<Schedule>> getSchedule(long stopId, long directionId) {
        return dbHelper.getSchedule(stopId, directionId);
    }

    @Override
    public Single<List<Stop>> getStopsOnDirection(long directionId) {
        return dbHelper.getStopsOnDirection(directionId);
    }

    @Override
    public Completable insertSearchHistoryStops(long stopId) {
        return dbHelper.insertSearchHistoryStops(stopId);
    }

    @Override
    public Single<List<Stop>> getSearchHistoryStops() {
        return dbHelper.getSearchHistoryStops();
    }

    @Override
    public Completable deleteSearchHistory() {
        return dbHelper.deleteSearchHistory();
    }

    @Override
    public Completable insertFavoriteStops(long stopId, long directionId) {
        return dbHelper.insertFavoriteStops(stopId, directionId);
    }

    @Override
    public Single<List<Stop>> getFavoriteStop() {
        return dbHelper.getFavoriteStop();
    }

    @Override
    public Single<List<Direction>> getFavoriteDirection(long stopId) {
        return dbHelper.getFavoriteDirection(stopId);
    }

    @Override
    public Single<String> getFlightNumber(long flightId) {
        return dbHelper.getFlightNumber(flightId);
    }

    @Override
    public Completable seedDatabaseFlights() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyFlight()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<Flight>>(){}.getType();
                            List<Flight> flights = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_FLIGHTS),type);
                            return saveFlightList(flights);
                        }
                        return Completable.complete();
                    }
                });
    }

    @Override
    public Completable seedDatabaseDirections() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyDirection()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(final Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<Direction>>(){}.getType();
                            List<Direction> directions = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_DIRECTIONS), type);
                            return saveDirectionList(directions);
                        }
                        return Completable.complete();
                    }
                });
    }

    @Override
    public Completable seedDatabaseStopsOnRouts() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyStopsOnRouts()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(final Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<StopsOnRouts>>(){}.getType();
                            List<StopsOnRouts> stopsOnRouts = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_STOPS_ON_ROUTS), type);
                            return saveStopsOnRoutsList(stopsOnRouts);
                        }
                        return Completable.complete();
                    }
                });
    }

    @Override
    public Completable seedDatabaseStops() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyStop()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(final Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<Stop>>(){}.getType();
                            List<Stop> stops = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_STOPS), type);
                            return saveStopList(stops);
                        }
                        return Completable.complete();
                    }
                });
    }

    @Override
    public Completable seedDatabaseSchedules() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptySchedule()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(final Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<Schedule>>(){}.getType();
                            List<Schedule> schedules = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_SCHEDULES), type);
                            return saveScheduleList(schedules);
                        }
                        return Completable.complete();
                    }
                });
    }

    @Override
    public Completable seedDatabaseDepartureTime() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyDepartureTime()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<DepartureTime>>(){}.getType();
                            List<DepartureTime> departureTimes = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_DEPARTURE_TIME), type);
                            return saveDepartureTimeList(departureTimes);
                        }
                        return Completable.complete();
                    }
                });
    }

    @Override
    public Completable seedAllTables() {
        return Completable.concat(getListCompletable());
    }

    private Iterable<CompletableSource> getListCompletable() {
        List<CompletableSource> completableSources = new ArrayList<>();
        completableSources.add(seedDatabaseFlights());
        completableSources.add(seedDatabaseDirections());
        completableSources.add(seedDatabaseStops());
        completableSources.add(seedDatabaseStopsOnRouts());
        completableSources.add(seedDatabaseSchedules());
        completableSources.add(seedDatabaseDepartureTime());
        return completableSources;
    }

    @Override
    public void setFirstRunVariable(boolean value) {
        preferencesHelper.setFirstRunVariable(value);
    }

    @Override
    public boolean getFirstRunVariable() {
        return preferencesHelper.getFirstRunVariable();
    }
}
