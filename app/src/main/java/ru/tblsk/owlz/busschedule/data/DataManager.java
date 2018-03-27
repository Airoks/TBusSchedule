package ru.tblsk.owlz.busschedule.data;


import io.reactivex.Completable;
import ru.tblsk.owlz.busschedule.data.db.DbHelper;
import ru.tblsk.owlz.busschedule.data.preferences.PreferencesHelper;

public interface DataManager extends DbHelper, PreferencesHelper{
    Completable seedDatabaseFlights();
    Completable seedDatabaseDirections();
    Completable seedDatabaseStopsOnRouts();
    Completable seedDatabaseStops();
    Completable seedDatabaseSchedules();
    Completable seedDatabaseDepartureTime();

    Completable seedAllTables();
}
