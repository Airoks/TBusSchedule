package ru.tblsk.owlz.busschedule.utils.mappers.viewobject;


import java.util.List;

public class DepartureTimeVO {
    private int hours;
    private List<Integer> minute;

    public DepartureTimeVO() {
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public List<Integer> getMinute() {
        return minute;
    }

    public void setMinute(List<Integer> minute) {
        this.minute = minute;
    }
}
