package ru.tblsk.owlz.busschedule.data;


import io.reactivex.Completable;
import ru.tblsk.owlz.busschedule.data.db.DbHelper;
import ru.tblsk.owlz.busschedule.data.preferences.PreferencesHelper;

public interface DataManager extends DbHelper, PreferencesHelper{
    Completable seedDatabaseFlightTypes();
    Completable seedDatabaseFlights();
    Completable seedDatabaseDirections();
    Completable seedDatabaseDirectionTypes();
    Completable seedDatabaseStopsOnRouts();
    Completable seedDatabaseStops();
    Completable seedDatabaseSchedules();
    Completable seedDatabaseScheduleTypes();
}
