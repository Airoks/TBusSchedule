package ru.tblsk.owlz.busschedule.data.db;


import java.util.List;

import io.reactivex.Observable;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.data.db.model.ScheduleType;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.data.db.model.StopsOnRouts;

public interface DbHelper {

    Observable<Boolean> saveFlightTypeList(List<FlightType> flightTypeList);
    Observable<Boolean> saveFlightList(List<Flight> flightList);
    Observable<Boolean> saveDirectionTypeList(List<DirectionType> directionTypeList);
    Observable<Boolean> saveDirectionList(List<Direction> directionList);
    Observable<Boolean> saveStopsOnRoutsList(List<StopsOnRouts> stopsOnRoutsList);
    Observable<Boolean> saveStopList(List<Stop> stopList);
    Observable<Boolean> saveScheduleList(List<Schedule> scheduleList);
    Observable<Boolean> saveScheduleTypeList(List<ScheduleType> scheduleTypeList);

    Observable<List<Stop>> getAllStops();
    Observable<List<Flight>> getFlightByType(String flightType);
    Observable<List<Direction>> getDirectionByStop(long stopId);
    Observable<List<Schedule>> getSchedule(long stopId, long directionId);
    Observable<List<Stop>> getAllStopsOnRouts(long directionId);

    Observable<Long> insertFavoriteStops(long stopId, long directionId);
    Observable<List<Stop>> getFavoriteStop();
    Observable<List<Direction>> getFavoriteDirection(long stopId);
    Observable<String> getFlightNumber(long flightId);
}
