package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.viewobject.StopVO;

public interface AllStopsMvpView extends MvpView{
    void showAllStops(List<StopVO> stops);
    void showSavedAllStops();
    void openStopInfoFragment(StopVO stop);
    void openPreviousFragment();
}
