package ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;

public interface BusScheduleContract {

    interface View extends MvpView {
        void showSchedule(DepartureTimeVO times);
        void showEmptyScreen();
        void setColorItem(int position);
        void resetColorItem();
    }

    interface Presenter extends MvpPresenter<View> {
        void getSchedule(long stopId, long directionId, int scheduleType);
        void cancelTimer();
        void startTimer();
    }

}
