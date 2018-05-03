package ru.tblsk.owlz.busschedule.ui.base;

import ru.tblsk.owlz.busschedule.utils.NextFlight;

public interface TimeUntilNextFlights {
    void getSchedule(long id);
    void getDepartureTime(long id, int scheduleType);
    void setTimer();
    NextFlight newNextFlight(NextFlight old);
    void getNextFlight(int position, boolean set);
}
