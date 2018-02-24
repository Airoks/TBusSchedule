package ru.tblsk.owlz.busschedule.data;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.App;
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
    private final Context context;
    private final DbHelper dbHelper;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public Single<Boolean> saveFlightTypeList(List<FlightType> flightTypeList) {
        return dbHelper.saveFlightTypeList(flightTypeList);
    }

    @Override
    public Single<Boolean> saveFlightList(List<Flight> flightList) {
        return dbHelper.saveFlightList(flightList);
    }

    @Override
    public Single<Boolean> saveDirectionTypeList(List<DirectionType> directionTypeList) {
        return dbHelper.saveDirectionTypeList(directionTypeList);
    }

    @Override
    public Single<Boolean> saveDirectionList(List<Direction> directionList) {
        return dbHelper.saveDirectionList(directionList);
    }

    @Override
    public Single<Boolean> saveStopsOnRoutsList(List<StopsOnRouts> stopsOnRoutsList) {
        return dbHelper.saveStopsOnRoutsList(stopsOnRoutsList);
    }

    @Override
    public Single<Boolean> saveStopList(List<Stop> stopList) {
        return dbHelper.saveStopList(stopList);
    }

    @Override
    public Single<Boolean> saveScheduleList(List<Schedule> scheduleList) {
        return dbHelper.saveScheduleList(scheduleList);
    }

    @Override
    public Single<Boolean> saveScheduleTypeList(List<ScheduleType> scheduleTypeList) {
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
    public Observable<List<Stop>> getAllStops() {
        return dbHelper.getAllStops();
    }

    @Override
    public Observable<List<Flight>> getFlightByType(String flightType) {
        return dbHelper.getFlightByType(flightType);
    }

    @Override
    public Observable<List<Direction>> getDirectionByStop(long stopId) {
        return dbHelper.getDirectionByStop(stopId);
    }

    @Override
    public Observable<List<Schedule>> getSchedule(long stopId, long directionId) {
        return dbHelper.getSchedule(stopId, directionId);
    }

    @Override
    public Observable<List<Stop>> getAllStopsOnRouts(long directionId) {
        return dbHelper.getAllStopsOnRouts(directionId);
    }

    @Override
    public Single<Long> insertFavoriteStops(long stopId, long directionId) {
        return dbHelper.insertFavoriteStops(stopId, directionId);
    }

    @Override
    public Observable<List<Stop>> getFavoriteStop() {
        return dbHelper.getFavoriteStop();
    }

    @Override
    public Observable<List<Direction>> getFavoriteDirection(long stopId) {
        return dbHelper.getFavoriteDirection(stopId);
    }

    @Override
    public Single<String> getFlightNumber(long flightId) {
        return dbHelper.getFlightNumber(flightId);
    }

    @Override
    public Single<Boolean> seedDatabaseFlightTypes() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyFlightType()
                .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                    @Override
                    public SingleSource<? extends Boolean> apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<FlightType>>(){}.getType();
                            List<FlightType> flightTypes = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(context,
                                            AppConstants.SEED_DB_FLIGHT_TYPES),type);
                            return saveFlightTypeList(flightTypes);
                        }
                        return Single.just(false);
                    }
                });
    }

    @Override
    public Single<Boolean> seedDatabaseFlights() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyFlight()
                .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                    @Override
                    public SingleSource<? extends Boolean> apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<Flight>>(){}.getType();
                            List<Flight> flights = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(context,
                                            AppConstants.SEED_DB_FLIGHTS),type);
                            return saveFlightList(flights);
                        }
                        return Single.just(false);
                    }
                });
    }

    @Override
    public Single<Boolean> seedDatabaseDirections() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyDirection()
                .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                    @Override
                    public SingleSource<? extends Boolean> apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<Direction>>(){}.getType();
                            List<Direction> directions = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(context,
                                            AppConstants.SEED_DB_DIRECTIONS), type);
                            return saveDirectionList(directions);
                        }
                        return Single.just(false);
                    }
                });
    }

    @Override
    public Single<Boolean> seedDatabaseDirectionTypes() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyDirectionType()
                .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                    @Override
                    public SingleSource<? extends Boolean> apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<DirectionType>>(){}.getType();
                            List<DirectionType> directionTypes = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(context,
                                            AppConstants.SEED_DB_DIRECTION_TYPES), type);
                            return saveDirectionTypeList(directionTypes);
                        }
                        return Single.just(false);
                    }
                });
    }

    @Override
    public Single<Boolean> seedDatabaseStopsOnRouts() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyStopsOnRouts()
                .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                    @Override
                    public SingleSource<? extends Boolean> apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<StopsOnRouts>>(){}.getType();
                            List<StopsOnRouts> stopsOnRouts = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(context,
                                            AppConstants.SEED_DB_STOPS_ON_ROUTS), type);
                            return saveStopsOnRoutsList(stopsOnRouts);
                        }
                        return Single.just(false);
                    }
                });
    }

    @Override
    public Single<Boolean> seedDatabaseStops() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyStop()
                .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                    @Override
                    public SingleSource<? extends Boolean> apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<Stop>>(){}.getType();
                            List<Stop> stops = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(context,
                                            AppConstants.SEED_DB_STOPS), type);
                            return saveStopList(stops);
                        }
                        return Single.just(false);
                    }
                });
    }

    @Override
    public Single<Boolean> seedDatabaseSchedules() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptySchedule()
                .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                    @Override
                    public SingleSource<? extends Boolean> apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<Schedule>>(){}.getType();
                            List<Schedule> schedules = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(context,
                                            AppConstants.SEED_DB_SCHEDULES), type);
                            return saveScheduleList(schedules);
                        }
                        return Single.just(false);
                    }
                });
    }

    @Override
    public Single<Boolean> seedDatabaseScheduleTypes() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return dbHelper.isEmptyScheduleType()
                .flatMap(new Function<Boolean, SingleSource<? extends Boolean>>() {
                    @Override
                    public SingleSource<? extends Boolean> apply(Boolean isEmpty) throws Exception {
                        if(isEmpty) {
                            Type type = new TypeToken<List<ScheduleType>>(){}.getType();
                            List<ScheduleType> scheduleTypes = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(context,
                                            AppConstants.SEED_DB_SCHEDULE_TYPES), type);
                            return saveScheduleTypeList(scheduleTypes);
                        }
                        return Single.just(false);
                    }
                });
    }
}
