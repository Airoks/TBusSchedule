package ru.tblsk.owlz.busschedule.data;


import io.reactivex.Single;
import ru.tblsk.owlz.busschedule.data.db.DbHelper;
import ru.tblsk.owlz.busschedule.data.preferences.PreferencesHelper;

public interface DataManager extends DbHelper, PreferencesHelper{
    Single<Boolean> seedDatabaseFlightTypes();
    Single<Boolean> seedDatabaseFlights();
    Single<Boolean> seedDatabaseDirections();
    Single<Boolean> seedDatabaseDirectionTypes();
    Single<Boolean> seedDatabaseStopsOnRouts();
    Single<Boolean> seedDatabaseStops();
    Single<Boolean> seedDatabaseSchedules();
    Single<Boolean> seedDatabaseScheduleTypes();
}
