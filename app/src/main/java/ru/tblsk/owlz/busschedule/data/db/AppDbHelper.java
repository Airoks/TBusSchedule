package ru.tblsk.owlz.busschedule.data.db;


import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import ru.tblsk.owlz.busschedule.data.db.model.DaoMaster;
import ru.tblsk.owlz.busschedule.data.db.model.DaoSession;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.data.db.model.ScheduleType;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.data.db.model.StopsOnRouts;

@Singleton
public class AppDbHelper implements DbHelper {
    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }
    @Override
    public Observable<Boolean> saveFlightTypeList(final List<FlightType> flightTypeList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getFlightTypeDao().insertInTx(flightTypeList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveFlightList(final List<Flight> flightList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getFlightDao().insertInTx(flightList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveDirectionTypeList(final List<DirectionType> directionTypeList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getDirectionTypeDao().insertInTx(directionTypeList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveDirectionList(final List<Direction> directionList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getDirectionDao().insertInTx(directionList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveStopsOnRoutsList(final List<StopsOnRouts> stopsOnRoutsList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getStopsOnRoutsDao().insertInTx(stopsOnRoutsList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveStopList(final List<Stop> stopList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getStopDao().insertInTx(stopList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveScheduleList(final List<Schedule> scheduleList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getScheduleDao().insertInTx(scheduleList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveScheduleTypeList(final List<ScheduleType> scheduleTypeList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getScheduleTypeDao().insertInTx(scheduleTypeList);
                return true;
            }
        });
    }

    @Override
    public Observable<List<Stop>> getAllStops() {
        return Observable.fromCallable(new Callable<List<Stop>>() {
            @Override
            public List<Stop> call() throws Exception {
                return mDaoSession.getStopDao().loadAll();
            }
        });
    }

    @Override
    public Observable<List<Flight>> getFlightByType(String journeyType) {
        return null;
    }

    @Override
    public Observable<List<Direction>> getDirectionByStop(int stopId) {
        return null;
    }

    @Override
    public Observable<List<Schedule>> getSchedule(int stopId, int directionId) {
        return null;
    }

    @Override
    public Observable<List<Stop>> getAllStopsOnRouts(int directionId) {
        return null;
    }
}
