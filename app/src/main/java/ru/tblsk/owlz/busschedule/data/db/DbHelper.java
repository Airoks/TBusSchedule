package ru.tblsk.owlz.busschedule.data.db;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.data.db.model.ScheduleType;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.data.db.model.StopsOnRouts;

public interface DbHelper {

    Completable saveFlightTypeList(List<FlightType> flightTypeList);
    Completable saveFlightList(List<Flight> flightList);
    Completable saveDirectionTypeList(List<DirectionType> directionTypeList);
    Completable saveDirectionList(List<Direction> directionList);
    Completable saveStopsOnRoutsList(List<StopsOnRouts> stopsOnRoutsList);
    Completable saveStopList(List<Stop> stopList);
    Completable saveScheduleList(List<Schedule> scheduleList);
    Completable saveScheduleTypeList(List<ScheduleType> scheduleTypeList);

    Single<Boolean> isEmptyDirection();
    Single<Boolean> isEmptyDirectionType();
    Single<Boolean> isEmptyFlight();
    Single<Boolean> isEmptyFlightType();
    Single<Boolean> isEmptySchedule();
    Single<Boolean> isEmptyScheduleType();
    Single<Boolean> isEmptyStop();
    Single<Boolean> isEmptyStopsOnRouts();

    Single<List<Stop>> getAllStops();
    Single<List<Flight>> getFlightByType(String flightType);
    Single<List<Direction>> getDirectionByStop(long stopId);
    Single<List<Schedule>> getSchedule(long stopId, long directionId);
    Single<List<Stop>> getAllStopsOnRouts(long directionId);

    Completable insertSearchHistoryStops(long stopId);
    Single<List<Stop>> getSearchHistoryStops();
    Completable deleteSearchHistory();

    Completable insertFavoriteStops(long stopId, long directionId);
    Single<List<Stop>> getFavoriteStop();
    Single<List<Direction>> getFavoriteDirection(long stopId);
    Single<String> getFlightNumber(long flightId);

    Single<String> getFlightTypeByDirection(long directionId);
}
