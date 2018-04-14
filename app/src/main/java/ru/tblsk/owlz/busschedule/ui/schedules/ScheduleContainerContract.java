package ru.tblsk.owlz.busschedule.ui.schedules;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface ScheduleContainerContract {

    interface View extends MvpView {
        void setToolbarTitle();
        void openPreviousFragment();
    }

    interface Presenter extends MvpPresenter<View> {
        void clickedOnBackButton();
    }

}
