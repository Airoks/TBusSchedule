package ru.tblsk.owlz.busschedule.ui.directioninfo;


import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;

public interface DirectionInfoMvpView extends MvpView{
    void showStopsOnDirection(List<Stop> stops);
    void showSavedStopsOnDirection();
    void showPreviousFragment();
    void updateFlight(FlightVO flight);
    void setDirectionTitle();
}
