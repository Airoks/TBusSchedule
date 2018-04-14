package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public interface AllStopsContract {

    interface View extends MvpView {
        void showAllStops(List<StopVO> stops);
        void showSavedAllStops();
        void openStopInfoFragment(StopVO stop);
        void openPreviousFragment();
    }

    interface Presenter extends MvpPresenter<View> {
        void getAllStops();
        void getSavedAllStops();
        void insertSearchHistoryStops(long stopId);
        void clickedOnBackButton();
        void setClickListenerForAdapter();
    }

}
