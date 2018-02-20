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
    Observable<List<Flight>> getFlightByType(String journeyType);
    Observable<List<Direction>> getDirectionByStop(int stopId);
    Observable<List<Schedule>> getSchedule(int stopId, int directionId);
    Observable<List<Stop>> getAllStopsOnRouts(int directionId);

}