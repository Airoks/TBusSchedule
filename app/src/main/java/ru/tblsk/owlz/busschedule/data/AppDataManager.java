package ru.tblsk.owlz.busschedule.data;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.db.DbHelper;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.data.db.model.ScheduleType;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.data.db.model.StopsOnRouts;
import ru.tblsk.owlz.busschedule.data.preferences.PreferencesHelper;
import ru.tblsk.owlz.busschedule.di.annotation.ApplicationContext;
import ru.tblsk.owlz.busschedule.utils.AppConstants;
import ru.tblsk.owlz.busschedule.utils.CommonUtils;

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
    public Completable saveFlightTypeList(List<FlightType> flightTypeList) {
        return dbHelper.saveFlightTypeList(flightTypeList);
    }

    @Override
    public Completable saveFlightList(List<Flight> flightList) {
        return dbHelper.saveFlightList(flightList);
    }

    @Override
    public Completable saveDirectionTypeList(List<DirectionType> directionTypeList) {
        return dbHelper.saveDirectionTypeList(directionTypeList);
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
    public Completable saveScheduleTypeList(List<ScheduleType> scheduleTypeList) {
        return dbHelper.saveScheduleTypeList(scheduleTypeList);
    }

    @Override
    public Single<Boolean> isEmptyDirection() {
        return dbHelper.isEmptyDirection();
    }

    @Override
    public Single<Boolean> isEmptyDirectionType() {
        return dbHelper.isEmptyDirectionType();
    }

    @Override
    public Single<Boolean> isEmptyFlight() {
        return dbHelper.isEmptyFlight();
    }

    @Override
    public Single<Boolean> isEmptyFlightType() {
        return dbHelper.isEmptyFlightType();
    }

    @Override
    public Single<Boolean> isEmptySchedule() {
        return dbHelper.isEmptySchedule();
    }

    @Override
    public Single<Boolean> isEmptyScheduleType() {
        return dbHelper.isEmptyScheduleType();
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
    public Single<List<Stop>> getAllStops() {
        return dbHelper.getAllStops();
    }

    @Override
    public Single<List<Flight>> getFlightByType(String flightType) {
        return dbHelper.getFlightByType(flightType);
    }

    @Override
    public Single<List<Direction>> getDirectionByStop(long stopId) {
        return dbHelper.getDirectionByStop(stopId);
    }

    @Override
    public Single<List<Schedule>> getSchedule(long stopId, long directionId) {
        return dbHelper.getSchedule(stopId, directionId);
    }

    @Override
    public Single<List<Stop>> getAllStopsOnRouts(long directionId) {
        return dbHelper.getAllStopsOnRouts(directionId);
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
    public Completable seedDatabaseFlightTypes() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyFlightType()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<FlightType>>() {
                            }.getType();
                            List<FlightType> flightTypes = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_FLIGHT_TYPES), type);
                            return saveFlightTypeList(flightTypes);
                        }
                        return Completable.complete();
                    }
                });
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
    public Completable seedDatabaseDirectionTypes() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyDirectionType()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(final Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<DirectionType>>(){}.getType();
                            List<DirectionType> directionTypes = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_DIRECTION_TYPES), type);
                            return saveDirectionTypeList(directionTypes);
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
    public Completable seedDatabaseScheduleTypes() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyScheduleType()
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<ScheduleType>>(){}.getType();
                            List<ScheduleType> scheduleTypes = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DB_SCHEDULE_TYPES), type);
                            return saveScheduleTypeList(scheduleTypes);
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
        completableSources.add(seedDatabaseFlightTypes());
        completableSources.add(seedDatabaseFlights());
        completableSources.add(seedDatabaseDirectionTypes());
        completableSources.add(seedDatabaseDirections());
        completableSources.add(seedDatabaseStops());
        completableSources.add(seedDatabaseStopsOnRouts());
        completableSources.add(seedDatabaseScheduleTypes());
        completableSources.add(seedDatabaseSchedules());
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
