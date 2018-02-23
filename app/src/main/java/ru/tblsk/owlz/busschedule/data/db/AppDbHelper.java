package ru.tblsk.owlz.busschedule.data.db;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import ru.tblsk.owlz.busschedule.data.db.model.DaoMaster;
import ru.tblsk.owlz.busschedule.data.db.model.DaoSession;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionDao;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.FavoriteStops;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightDao;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.data.db.model.FlightTypeDao;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.data.db.model.ScheduleType;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.data.db.model.StopDao;
import ru.tblsk.owlz.busschedule.data.db.model.StopsOnRouts;
import ru.tblsk.owlz.busschedule.data.db.model.StopsOnRoutsDao;

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
    public Observable<List<Flight>> getFlightByType(final String flightType) {
        return Observable.fromCallable(new Callable<List<Flight>>() {
            @Override
            public List<Flight> call() throws Exception {
                List<Flight> flights;
                long flightTypeId = mDaoSession.getFlightTypeDao().queryBuilder()
                        .where(FlightTypeDao.Properties.FlightTypeId.eq(flightType))
                        .unique().getFlightTypeId();
                flights = mDaoSession.getFlightDao().queryBuilder()
                        .where(FlightDao.Properties.FlightTypeId.eq(flightTypeId))
                        .list();
                return flights;
            }
        });
    }

    @Override
    public Observable<List<Direction>> getDirectionByStop(final long stopId) {
        return Observable.fromCallable(new Callable<List<Direction>>() {
            @Override
            public List<Direction> call() throws Exception {
                List<Direction> directions = Collections.emptyList();
                List<StopsOnRouts> stopsOnRouts = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                        .where(StopsOnRoutsDao.Properties.StopId.eq(stopId)).list();
                for(StopsOnRouts routs : stopsOnRouts) {
                    long id = routs.getDirectionId();
                    directions.add(mDaoSession.getDirectionDao().queryBuilder()
                    .where(DirectionDao.Properties.DirectionId.eq(id)).unique());
                }
                return directions;
            }
        });
    }

    @Override
    public Observable<List<Schedule>> getSchedule(final long stopId, final long directionId) {
        return Observable.fromCallable(new Callable<List<Schedule>>() {
            @Override
            public List<Schedule> call() throws Exception {
                List<Schedule> schedules;
                StopsOnRouts stopOnRout = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                        .where(StopsOnRoutsDao.Properties.StopId.eq(stopId),
                                StopsOnRoutsDao.Properties.DirectionId.eq(directionId)).unique();
                //what value return getSchedulers() if scheduler is not ??????
                schedules = stopOnRout.getSchedules();
                return schedules;
            }
        });
    }

    @Override
    public Observable<List<Stop>> getAllStopsOnRouts(final long directionId) {
        return Observable.fromCallable(new Callable<List<Stop>>() {
            @Override
            public List<Stop> call() throws Exception {
                List<Stop> stops = Collections.emptyList();
                List<StopsOnRouts> stopsOnRouts = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                        .where(StopsOnRoutsDao.Properties.DirectionId.eq(directionId))
                        .orderAsc(StopsOnRoutsDao.Properties.StopPosition).list();
                for(StopsOnRouts routs : stopsOnRouts) {
                    long id = routs.getStopId();
                    stops.add(mDaoSession.getStopDao().queryBuilder()
                    .where(StopDao.Properties.StopId.eq(id)).unique());
                }
                return stops;
            }
        });
    }

    @Override
    public Observable<Long> insertFavoriteStops(final long stopId, final long directionId) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long stopsOnRoutsId = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                        .where(StopsOnRoutsDao.Properties.StopId.eq(stopId),
                                StopsOnRoutsDao.Properties.DirectionId.eq(directionId))
                                .unique().getStopOnRoutsId();
                FavoriteStops favoriteStops = new FavoriteStops();
                favoriteStops.setStopsOnRoutsId(stopsOnRoutsId);
                return mDaoSession.getFavoriteStopsDao().insert(favoriteStops);
            }
        });
    }

    @Override
    public Observable<List<Stop>> getFavoriteStop() {
        return Observable.fromCallable(new Callable<List<Stop>>() {
            @Override
            public List<Stop> call() throws Exception {
                Set<Long> stopsId = new HashSet<>();
                List<FavoriteStops> favorites = mDaoSession.getFavoriteStopsDao().loadAll();
                List<Stop> stops = new ArrayList<>();

                //search favoriteStopId
                for(FavoriteStops favorite : favorites) {
                    Long id = favorite.getStopsOnRouts().getStopId();
                    stopsId.add(id);
                }
                for(Long id : stopsId) {
                    stops.add(mDaoSession.getStopDao().queryBuilder()
                    .where(StopDao.Properties.StopId.eq(id)).unique());
                }
                return stops;
            }
        });
    }

    @Override
    public Observable<List<Direction>> getFavoriteDirection(final long stopId) {
        return Observable.fromCallable(new Callable<List<Direction>>() {
            @Override
            public List<Direction> call() throws Exception {
                List<Direction> directions = new ArrayList<>();
                List<FavoriteStops> favorites = mDaoSession.getFavoriteStopsDao().loadAll();

                for(FavoriteStops favorite : favorites) {
                    Long id = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                            .where(StopsOnRoutsDao.Properties.StopOnRoutsId.eq(favorite.getFavoriteStopsId()),
                                    StopsOnRoutsDao.Properties.StopId.eq(stopId)).unique().getDirectionId();
                    directions.add(mDaoSession.getDirectionDao().queryBuilder()
                    .where(DirectionDao.Properties.DirectionId.eq(id)).unique());
                }
                return directions;
            }
        });
    }

    @Override
    public Observable<String> getFlightNumber(final long flightId) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mDaoSession.getFlightDao().queryBuilder()
                        .where(FlightDao.Properties.FlightId.eq(flightId)).unique().getFlightNumber();
            }
        });
    }
}
