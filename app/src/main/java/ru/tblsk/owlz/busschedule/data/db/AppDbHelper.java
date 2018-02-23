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
import io.reactivex.Single;
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
    public Single<Boolean> saveFlightTypeList(final List<FlightType> flightTypeList) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getFlightTypeDao().insertInTx(flightTypeList);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> saveFlightList(final List<Flight> flightList) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getFlightDao().insertInTx(flightList);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> saveDirectionTypeList(final List<DirectionType> directionTypeList) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getDirectionTypeDao().insertInTx(directionTypeList);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> saveDirectionList(final List<Direction> directionList) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getDirectionDao().insertInTx(directionList);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> saveStopsOnRoutsList(final List<StopsOnRouts> stopsOnRoutsList) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getStopsOnRoutsDao().insertInTx(stopsOnRoutsList);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> saveStopList(final List<Stop> stopList) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getStopDao().insertInTx(stopList);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> saveScheduleList(final List<Schedule> scheduleList) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getScheduleDao().insertInTx(scheduleList);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> saveScheduleTypeList(final List<ScheduleType> scheduleTypeList) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getScheduleTypeDao().insertInTx(scheduleTypeList);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> isEmptyDirection() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getDirectionDao().count() > 0);
            }
        });
    }

    @Override
    public Single<Boolean> isEmptyDirectionType() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getDirectionTypeDao().count() > 0);
            }
        });
    }

    @Override
    public Single<Boolean> isEmptyFlight() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getFlightDao().count() > 0);
            }
        });
    }

    @Override
    public Single<Boolean> isEmptyFlightType() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getFlightTypeDao().count() > 0);
            }
        });
    }

    @Override
    public Single<Boolean> isEmptySchedule() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getScheduleDao().count() > 0);
            }
        });
    }

    @Override
    public Single<Boolean> isEmptyScheduleType() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getScheduleTypeDao().count() > 0);
            }
        });
    }

    @Override
    public Single<Boolean> isEmptyStop() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getStopDao().count() > 0);
            }
        });
    }

    @Override
    public Single<Boolean> isEmptyStopsOnRouts() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getStopsOnRoutsDao().count() > 0);
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
    public Single<Long> insertFavoriteStops(final long stopId, final long directionId) {
        return Single.fromCallable(new Callable<Long>() {
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
    public Single<String> getFlightNumber(final long flightId) {
        return Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mDaoSession.getFlightDao().queryBuilder()
                        .where(FlightDao.Properties.FlightId.eq(flightId)).unique().getFlightNumber();
            }
        });
    }
}
