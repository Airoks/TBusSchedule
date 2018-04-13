package ru.tblsk.owlz.busschedule.ui.directioninfo;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public interface DirectionInfoMvpView extends MvpView{
    void showStopsOnDirection(List<StopVO> stops);
    void openPreviousFragment();
    void openScheduleContainerFragment(StopVO stop, FlightVO flight);
    void setDirectionTitle(String directionName);
    void setToolbarTitle(String flightNumber);
    void showChangeButton(boolean flag);
}
