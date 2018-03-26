package ru.tblsk.owlz.busschedule.ui.directioninfo;


import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.Stop;

public class ChangeDirectionInfo {
    private List<Stop> stops;

    public ChangeDirectionInfo(List<Stop> stops) {
        this.stops = stops;
    }

    public List<Stop> getStops() {
        return stops;
    }
}
