package ru.tblsk.owlz.busschedule.data;


import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
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
}
