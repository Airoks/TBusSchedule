package ru.tblsk.owlz.busschedule.utils.mappers.viewobject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartureTimeVO {
    private List<Integer> hours;
    private Map<Integer, ArrayList<Integer>> time;

    public DepartureTimeVO() {
        hours = new ArrayList<>();
        time = new HashMap<>();
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours.addAll(hours);
    }

    public Map<Integer, ArrayList<Integer>> getTime() {
        return time;
    }

    public void setTime(Map<Integer, ArrayList<Integer>> time) {
        this.time.putAll(time);
    }

    public boolean isEmpty() {
        return hours.isEmpty();
    }
}
