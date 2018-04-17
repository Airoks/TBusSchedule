package ru.tblsk.owlz.busschedule.ui.busschedulescreen;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface BusScheduleContainerContract {

    interface View extends MvpView {
        void setToolbarTitle();
        void openPreviousFragment();
        void openStopInfoFragment();
        void openDirectionInfoFragment();
    }

    interface Presenter extends MvpPresenter<View> {
        void clickedOnBackButton();
        void clickedOnDirection(String currentTopTag);
        void clickedOnBusStop(String currentTopTag);
    }

}
