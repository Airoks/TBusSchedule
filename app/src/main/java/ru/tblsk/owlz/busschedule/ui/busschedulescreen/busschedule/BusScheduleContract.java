package ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DepartureTimeVO;

public interface BusScheduleContract {

    interface View extends MvpView {
        void showSchedule(List<DepartureTimeVO> times);
        void showEmptyScreen();
    }

    interface Presenter extends MvpPresenter<View> {
        void getSchedule(long stopId, long directionId, int scheduleType);
    }

}
