package ru.tblsk.owlz.busschedule.data.db;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.data.db.model.StopsOnRouts;

public interface DbHelper {

    Completable saveFlightList(List<Flight> flightList);
    Completable saveDirectionList(List<Direction> directionList);
    Completable saveStopsOnRoutsList(List<StopsOnRouts> stopsOnRoutsList);
    Completable saveStopList(List<Stop> stopList);
    Completable saveScheduleList(List<Schedule> scheduleList);
    Completable saveDepartureTimeList(List<DepartureTime> departureTimes);

    Single<Boolean> isEmptyDirection();
    Single<Boolean> isEmptyFlight();
    Single<Boolean> isEmptySchedule();
    Single<Boolean> isEmptyStop();
    Single<Boolean> isEmptyStopsOnRouts();
    Single<Boolean> isEmptyDepartureTime();

    Single<List<Stop>> getAllStops();
    Single<List<Flight>> getFlightByType(int flightType);
    Single<List<Direction>> getDirectionsByStop(long stopId);
    Single<DepartureTime> getSchedule(long stopId, long directionId, int scheduleType);

    Observable<DepartureTime> getScheduleByDirection(long directionId, int scheduleType);
    Observable<DepartureTime> getScheduleByStop(long stopId, int scheduleType);
    Observable<DepartureTime> getScheduleByFavoriteDirections(long stopId, List<Long> directions,
                                                              int scheduleType);

    Single<List<Stop>> getStopsOnDirection(long directionId);

    Completable insertSearchHistoryStops(long stopId);
    Single<List<Stop>> getSearchHistoryStops();
    Completable deleteSearchHistory();

    Completable insertFavoriteStops(long stopId, List<Long> directions);
    Completable deleteFavoriteStop(long stopId);
    Single<List<Stop>> getFavoriteStop();
    Single<List<Direction>> getFavoriteDirection(long stopId);
    Single<Boolean> isFavoriteStop(long stopId);
    Single<Flight> getFlightByDirection(long directionId);

    Single<List<String>> getFlightNumbers(List<Direction> directions);

}
