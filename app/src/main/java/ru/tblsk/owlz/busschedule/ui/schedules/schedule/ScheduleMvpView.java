package ru.tblsk.owlz.busschedule.ui.schedules.schedule;


import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface ScheduleMvpView extends MvpView{
    void showSchedule(List<DepartureTime> times);
}
