package ru.tblsk.owlz.busschedule.utils;



public final class AppConstants {
    public static final String DB_NAME = "bus_schedule.db";
    public static final String PREFERENCES_NAME = "schedule_pref";

    public static final String SEED_DB_DIRECTIONS = "db/Direction.json";
    public static final String SEED_DB_FLIGHTS = "db/Flight.json";
    public static final String SEED_DB_SCHEDULES = "db/Schedule.json";
    public static final String SEED_DB_DEPARTURE_TIME = "db/DepartureTime.json";
    public static final String SEED_DB_STOPS = "db/Stop.json";
    public static final String SEED_DB_STOPS_ON_ROUTS = "db/StopOnRouts.json";

    public static final int STOPS_ADAPTER = 0;
    public static final int ALL_STOPS_ADAPTER = 1;

    public static final int URBAN = 0;
    public static final int SUBURBAN = 1;

    private AppConstants() {

    }
}
