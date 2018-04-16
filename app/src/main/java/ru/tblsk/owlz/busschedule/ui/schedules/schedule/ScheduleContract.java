package ru.tblsk.owlz.busschedule.ui.schedules.schedule;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DepartureTimeVO;

public interface ScheduleContract {

    interface View extends MvpView {
        void showSchedule(List<DepartureTimeVO> times);
        void showEmptyScreen();
    }

    interface Presenter extends MvpPresenter<View> {
        void getSchedule(long stopId, long directionId, int scheduleType);
    }

}
