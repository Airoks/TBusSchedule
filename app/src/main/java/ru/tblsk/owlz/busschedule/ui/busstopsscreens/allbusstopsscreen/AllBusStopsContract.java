package ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;

public interface AllBusStopsContract {

    interface View extends MvpView {
        void showAllStops(List<StopVO> stops);
        void openStopInfoFragment(StopVO stop);
        void openPreviousFragment();
        void showEmptyScreen();
    }

    interface Presenter extends MvpPresenter<View> {
        void getAllStops();
        void insertSearchHistoryStops(long stopId);
        void clickedOnBackButton();
        void clickedOnAdapterItem(int position);
    }

}
