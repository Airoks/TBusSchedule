package ru.tblsk.owlz.busschedule.utils;

public class NextFlight {
    private int hour;
    private int minute;
    private int timeBeforeDeparture;
    private boolean initialized;

    public NextFlight() {
        initialized = false;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getTimeBeforeDeparture() {
        return timeBeforeDeparture;
    }

    public void setTimeBeforeDeparture(int timeBeforeDeparture) {
        this.timeBeforeDeparture = timeBeforeDeparture;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
