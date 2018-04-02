package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface AllStopsMvpView extends MvpView{
    void showAllStops(List<Stop> stops);
    void showSavedAllStops();
}
