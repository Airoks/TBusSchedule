package ru.tblsk.owlz.busschedule.ui.schedules.schedule;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface ScheduleMvpPresenter<V extends ScheduleMvpView>
        extends MvpPresenter<V> {
    void getSchedule(long stopId, long directionId, int scheduleType);
    void clearData();
}
