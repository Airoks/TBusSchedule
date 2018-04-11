package ru.tblsk.owlz.busschedule.ui.stops.historystops;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public interface StopsMvpView extends MvpView{
    void showSearchHistoryStops(List<StopVO> stops);
    void showAllStopsFragment();
    void openStopInfoFragment(StopVO stop);
}
