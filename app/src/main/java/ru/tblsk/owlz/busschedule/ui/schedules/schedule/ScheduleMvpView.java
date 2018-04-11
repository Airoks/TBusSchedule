package ru.tblsk.owlz.busschedule.ui.schedules.schedule;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DepartureTimeVO;

public interface ScheduleMvpView extends MvpView{
    void showSchedule(List<DepartureTimeVO> times);
    void showEmptyScreen();
}
