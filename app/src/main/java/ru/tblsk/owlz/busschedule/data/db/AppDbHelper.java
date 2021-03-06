package ru.tblsk.owlz.busschedule.data.db;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import ru.tblsk.owlz.busschedule.data.db.model.DaoMaster;
import ru.tblsk.owlz.busschedule.data.db.model.DaoSession;
import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionDao;
import ru.tblsk.owlz.busschedule.data.db.model.FavoriteStops;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightDao;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.data.db.model.SearchHistoryStops;
import ru.tblsk.owlz.busschedule.data.db.model.SearchHistoryStopsDao;
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
    public Completable saveFlightList(final List<Flight> flightList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDaoSession.getFlightDao().insertInTx(flightList);
            }
        });
    }

    @Override
    public Completable saveDirectionList(final List<Direction> directionList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDaoSession.getDirectionDao().insertInTx(directionList);
            }
        });
    }

    @Override
    public Completable saveStopsOnRoutsList(final List<StopsOnRouts> stopsOnRoutsList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDaoSession.getStopsOnRoutsDao().insertInTx(stopsOnRoutsList);
            }
        });
    }

    @Override
    public Completable saveStopList(final List<Stop> stopList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDaoSession.getStopDao().insertInTx(stopList);
            }
        });
    }

    @Override
    public Completable saveScheduleList(final List<Schedule> scheduleList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDaoSession.getScheduleDao().insertInTx(scheduleList);
            }
        });
    }

    @Override
    public Completable saveDepartureTimeList(final List<DepartureTime> departureTimes) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDaoSession.getDepartureTimeDao().insertInTx(departureTimes);
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
    public Single<Boolean> isEmptyFlight() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getFlightDao().count() > 0);
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
    public Single<Boolean> isEmptyDepartureTime() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getDepartureTimeDao().count() > 0);
            }
        });
    }

    @Override
    public Single<List<Stop>> getAllStops() {
        return Single.fromCallable(new Callable<List<Stop>>() {
            @Override
            public List<Stop> call() throws Exception {
                return mDaoSession.getStopDao().loadAll();
            }
        });
    }

    @Override
    public Single<List<Flight>> getFlightByType(final int flightType) {
        return Single.fromCallable(new Callable<List<Flight>>() {
            @Override
            public List<Flight> call() throws Exception {
                return mDaoSession.getFlightDao().queryBuilder()
                        .where(FlightDao.Properties.FlightType.eq(flightType))
                        .list();
            }
        });
    }

    @Override
    public Single<List<Direction>> getDirectionsByStop(final long stopId) {
        return Single.fromCallable(new Callable<List<Direction>>() {
            @Override
            public List<Direction> call() throws Exception {
                List<Direction> directions = new ArrayList<>();
                Stop stop = mDaoSession.getStopDao().load(stopId);
                List<StopsOnRouts> stopsOnRouts = stop.getStopsOnRouts();

                Collections.sort(stopsOnRouts, new Comparator<StopsOnRouts>() {
                    @Override
                    public int compare(StopsOnRouts left, StopsOnRouts right) {
                        return (int) (left.getId() - right.getId());
                    }
                });

                for(StopsOnRouts routs : stopsOnRouts) {
                    long directionId = routs.getDirectionId();
                    directions.add(mDaoSession.getDirectionDao().queryBuilder()
                    .where(DirectionDao.Properties.Id.eq(directionId)).unique());
                }
                return directions;
            }
        });
    }

    @Override
    public Single<DepartureTime> getSchedule(final long stopId,
                                             final long directionId,
                                             final int scheduleType) {
        return Single.fromCallable(new Callable<DepartureTime>() {
            @Override
            public DepartureTime call() throws Exception {
                List<Schedule> schedules;
                DepartureTime departureTimes = new DepartureTime();

                StopsOnRouts stopOnRout = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                        .where(StopsOnRoutsDao.Properties.StopId.eq(stopId),
                                StopsOnRoutsDao.Properties.DirectionId.eq(directionId)).unique();
                schedules = stopOnRout.getSchedules();

                for(Schedule schedule : schedules) {
                    int type = schedule.getScheduleType().id;
                    boolean check = !schedule.getDepartureTimes().isEmpty();
                    if((type == scheduleType) && check) {
                        departureTimes = schedule.getDepartureTimes().get(0);
                    }
                }
                return departureTimes;
            }
        });
    }

    @Override
    public Observable<DepartureTime> getScheduleByDirection(final long directionId,
                                                            final int scheduleType) {
        return Observable.create(new ObservableOnSubscribe<DepartureTime>() {
            @Override
            public void subscribe(ObservableEmitter<DepartureTime> e) throws Exception {
                Direction direction = mDaoSession.getDirectionDao().load(directionId);
                List<StopsOnRouts> stopsOnRouts = direction.getStopsOnRouts();
                Collections.sort(stopsOnRouts, new Comparator<StopsOnRouts>() {
                    @Override
                    public int compare(StopsOnRouts left, StopsOnRouts right) {
                        return left.getStopPosition() - right.getStopPosition();
                    }
                });

                for(StopsOnRouts onRouts : stopsOnRouts) {
                    List<Schedule> schedules;
                    DepartureTime departureTimes = new DepartureTime();

                    schedules = onRouts.getSchedules();
                    for(Schedule schedule : schedules) {
                        int type = schedule.getScheduleType().id;
                        boolean check = !schedule.getDepartureTimes().isEmpty();
                        if((type == scheduleType) && check) {
                            departureTimes = schedule.getDepartureTimes().get(0);
                        }
                    }
                    e.onNext(departureTimes);
                }
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<DepartureTime> getScheduleByStop(final long stopId,
                                                       final int scheduleType) {
        return Observable.create(new ObservableOnSubscribe<DepartureTime>() {
            @Override
            public void subscribe(ObservableEmitter<DepartureTime> e) throws Exception {
                Stop stop = mDaoSession.getStopDao().load(stopId);
                List<StopsOnRouts> stopsOnRouts = stop.getStopsOnRouts();

                Collections.sort(stopsOnRouts, new Comparator<StopsOnRouts>() {
                    @Override
                    public int compare(StopsOnRouts left, StopsOnRouts right) {
                        return (int) (left.getId() - right.getId());
                    }
                });

                for(StopsOnRouts onRouts : stopsOnRouts) {
                    List<Schedule> schedules;
                    DepartureTime departureTimes = new DepartureTime();

                    schedules = onRouts.getSchedules();
                    for(Schedule schedule : schedules) {
                        int type = schedule.getScheduleType().id;
                        boolean check = !schedule.getDepartureTimes().isEmpty();
                        if((type == scheduleType) && check) {
                            departureTimes = schedule.getDepartureTimes().get(0);
                        }
                    }
                    e.onNext(departureTimes);
                }
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<DepartureTime> getScheduleByFavoriteDirections(final long stopId,
                                                                     final List<Long> directions,
                                                                     final int scheduleType) {
        return Observable.create(new ObservableOnSubscribe<DepartureTime>() {
            @Override
            public void subscribe(ObservableEmitter<DepartureTime> e) throws Exception {
                List<Schedule> schedules;
                for(long directionId : directions) {
                    DepartureTime departureTimes = new DepartureTime();
                    StopsOnRouts stopsOnRouts = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                            .where(StopsOnRoutsDao.Properties.StopId.eq(stopId),
                                    StopsOnRoutsDao.Properties.DirectionId.eq(directionId)).unique();

                    schedules = stopsOnRouts.getSchedules();
                    for(Schedule schedule : schedules) {
                        int type = schedule.getScheduleType().id;
                        boolean check = !schedule.getDepartureTimes().isEmpty();
                        if((type == scheduleType) && check) {
                            departureTimes = schedule.getDepartureTimes().get(0);
                        }
                    }
                    e.onNext(departureTimes);
                }
                e.onComplete();

            }
        });
    }

    @Override
    public Single<List<Stop>> getStopsOnDirection(final long directionId) {
        return Single.fromCallable(new Callable<List<Stop>>() {
            @Override
            public List<Stop> call() throws Exception {
                List<Stop> stops = new ArrayList<>();
                List<StopsOnRouts> stopsOnRouts = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                        .where(StopsOnRoutsDao.Properties.DirectionId.eq(directionId))
                        .orderAsc(StopsOnRoutsDao.Properties.StopPosition).list();
                for(StopsOnRouts routs : stopsOnRouts) {
                    long stopId = routs.getStopId();
                    stops.add(mDaoSession.getStopDao().queryBuilder()
                    .where(StopDao.Properties.Id.eq(stopId)).unique());
                }
                return stops;
            }
        });
    }

    @Override
    public Completable insertSearchHistoryStops(final long stopId) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                if(!containsStop(stopId)) {
                    SearchHistoryStops searchHistoryStops = new SearchHistoryStops();
                    searchHistoryStops.setStopId(stopId);
                    mDaoSession.getSearchHistoryStopsDao().insert(searchHistoryStops);
                }
            }
        });
    }

    private boolean containsStop(long stopId) {
        SearchHistoryStops searchHS = mDaoSession.getSearchHistoryStopsDao().queryBuilder()
                .where(SearchHistoryStopsDao.Properties.StopId.eq(stopId)).unique();
        return searchHS != null;
    }

    @Override
    public Single<List<Stop>> getSearchHistoryStops() {
        return Single.fromCallable(new Callable<List<Stop>>() {
            @Override
            public List<Stop> call() throws Exception {
                List<Stop> stops = new ArrayList<>();
                List<SearchHistoryStops> searchHistoryStops = mDaoSession
                        .getSearchHistoryStopsDao().loadAll();
                for(SearchHistoryStops searchHS : searchHistoryStops) {
                    long id = searchHS.getStopId();
                    stops.add(mDaoSession.getStopDao().queryBuilder()
                            .where(StopDao.Properties.Id.eq(id)).unique());
                }
                return stops;
            }
        });
    }

    @Override
    public Completable deleteSearchHistory() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDaoSession.getSearchHistoryStopsDao().deleteAll();
                mDaoSession.getSearchHistoryStopsDao().detachAll();
            }
        });
    }

    @Override
    public Completable insertFavoriteStops(final long stopId, final List<Long> directions) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                for(Long directionId : directions) {
                    //сохраняем только остановку с оперделенным направлением
                    long stopsOnRoutsId = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                            .where(StopsOnRoutsDao.Properties.StopId.eq(stopId),
                                    StopsOnRoutsDao.Properties.DirectionId.eq(directionId))
                            .unique().getId();
                    FavoriteStops favoriteStops = new FavoriteStops();
                    favoriteStops.setStopsOnRoutsId(stopsOnRoutsId);
                    mDaoSession.getFavoriteStopsDao().insert(favoriteStops);
                }
            }
        });
    }

    @Override
    public Completable deleteFavoriteStop(final long stopId) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                List<FavoriteStops> favorites = mDaoSession.getFavoriteStopsDao().loadAll();

                for(FavoriteStops favorite : favorites) {
                    StopsOnRouts stopsOnRouts = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                            .where(StopsOnRoutsDao.Properties.Id.eq(favorite.getStopsOnRoutsId()),
                                    StopsOnRoutsDao.Properties.StopId.eq(stopId)).unique();
                    if(stopsOnRouts != null) {
                        mDaoSession.getFavoriteStopsDao().deleteByKey(favorite.getId());
                        //mDaoSession.getFavoriteStopsDao().detach(favorite);
                    }
                }
            }
        });
    }

    @Override
    public Single<List<Stop>> getFavoriteStop() {
        return Single.fromCallable(new Callable<List<Stop>>() {
            @Override
            public List<Stop> call() throws Exception {
                Set<Long> stopsId = new HashSet<>();
                List<FavoriteStops> favorites = mDaoSession.getFavoriteStopsDao().loadAll();
                List<Stop> stops = new ArrayList<>();

                //search favoriteStopId
                for(FavoriteStops favorite : favorites) {
                    Long stopId = favorite.getStopsOnRouts().getStopId();
                    stopsId.add(stopId);
                }
                for(Long stopId : stopsId) {
                    stops.add(mDaoSession.getStopDao().queryBuilder()
                    .where(StopDao.Properties.Id.eq(stopId)).unique());
                }
                return stops;
            }
        });
    }

    @Override
    public Single<List<Direction>> getFavoriteDirection(final long stopId) {
        return Single.fromCallable(new Callable<List<Direction>>() {
            @Override
            public List<Direction> call() throws Exception {
                List<Direction> directions = new ArrayList<>();
                List<FavoriteStops> favoriteStops = mDaoSession.getFavoriteStopsDao().loadAll();
                List<StopsOnRouts> stopsOnRouts = new ArrayList<>();

                for(FavoriteStops favorite : favoriteStops) {
                    if(favorite.getStopsOnRouts().getStopId().equals(stopId)) {
                        stopsOnRouts.add(favorite.getStopsOnRouts());
                    }
                }

                Collections.sort(stopsOnRouts, new Comparator<StopsOnRouts>() {
                    @Override
                    public int compare(StopsOnRouts left, StopsOnRouts right) {
                        return (int) (left.getId() - right.getId());
                    }
                });

                for(StopsOnRouts onRouts : stopsOnRouts) {
                    long directionId = onRouts.getDirectionId();
                    directions.add(mDaoSession.getDirectionDao().queryBuilder()
                            .where(DirectionDao.Properties.Id.eq(directionId)).unique());
                }
                return directions;
            }
        });
    }

    @Override
    public Single<Boolean> isFavoriteStop(final long stopId) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                List<FavoriteStops> favorites = mDaoSession.getFavoriteStopsDao().loadAll();

                for(FavoriteStops favorite : favorites) {
                    StopsOnRouts stopsOnRouts = mDaoSession.getStopsOnRoutsDao().queryBuilder()
                            .where(StopsOnRoutsDao.Properties.Id.eq(favorite.getStopsOnRoutsId()),
                                    StopsOnRoutsDao.Properties.StopId.eq(stopId)).unique();
                    if(stopsOnRouts != null) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public Single<Flight> getFlightByDirection(final long directionId) {
        return Single.fromCallable(new Callable<Flight>() {
            @Override
            public Flight call() throws Exception {
                long flightId = mDaoSession.getDirectionDao().queryBuilder()
                        .where(DirectionDao.Properties.Id.eq(directionId)).unique().getFlightId();
                return mDaoSession.getFlightDao().load(flightId);
            }
        });
    }

    @Override
    public Single<List<String>> getFlightNumbers(final List<Direction> directions) {
        List<String> flightNumbers = new ArrayList<>();
        for(Direction direction : directions) {
            String flightNumber = mDaoSession.getFlightDao().queryBuilder()
                    .where(FlightDao.Properties.Id.eq(direction.getFlightId())).unique().getFlightNumber();
            flightNumbers.add(flightNumber);
        }
        return Single.just(flightNumbers);
    }

}
