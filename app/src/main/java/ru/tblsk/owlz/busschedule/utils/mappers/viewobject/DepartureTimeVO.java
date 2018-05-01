package ru.tblsk.owlz.busschedule.utils.mappers.viewobject;


import java.util.ArrayList;
import java.util.List;

public class DepartureTimeVO {
    private List<Integer> hours;
    private List<Integer> minute;

    public DepartureTimeVO() {
        hours = new ArrayList<>();
        minute = new ArrayList<>();
    }

    public List<Integer> getMinute() {
        return minute;
    }

    public void setMinute(List<Integer> minute) {
        this.minute.addAll(minute);
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours.addAll(hours);
    }
}
