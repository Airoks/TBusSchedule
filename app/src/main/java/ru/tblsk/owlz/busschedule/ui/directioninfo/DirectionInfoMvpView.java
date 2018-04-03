package ru.tblsk.owlz.busschedule.ui.directioninfo;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.viewobject.StopVO;

public interface DirectionInfoMvpView extends MvpView{
    void showStopsOnDirection(List<StopVO> stops);
    void showSavedStopsOnDirection();
    void openPreviousFragment();
    void updateFlight(FlightVO flight);
    void setDirectionTitle();
}
