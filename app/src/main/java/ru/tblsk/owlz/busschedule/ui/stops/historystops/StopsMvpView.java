package ru.tblsk.owlz.busschedule.ui.stops.historystops;


import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface StopsMvpView extends MvpView{
    void showSearchHistoryStops(List<Stop> stops);
    void showAllStopsFragment();
}
