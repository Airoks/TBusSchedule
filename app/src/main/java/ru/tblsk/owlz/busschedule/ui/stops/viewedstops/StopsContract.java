package ru.tblsk.owlz.busschedule.ui.stops.viewedstops;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public interface StopsContract {

    interface View extends MvpView {
        void showSearchHistoryStops(List<StopVO> stops);
        void showAllStopsFragment();
        void openStopInfoFragment(StopVO stop);
        void openNavigationDrawer();
    }

    interface Presenter extends MvpPresenter<View> {
        void getSearchHistoryStops();
        void deleteSearchHistoryStops();
        void clickedOnAllStopsButton();
        void clickedOnNavigation();
        void clickedOnAdapterItem(int position);
    }
}
