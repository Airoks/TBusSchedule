package ru.tblsk.owlz.busschedule.ui.main;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface MainContract {

    interface View extends MvpView {
        void openFavoriteBusStopsFragment();
        void openViewedBusStopsFragment();
        void openBusRoutesContainerFragment();
    }

    interface Presenter extends MvpPresenter<View> {
        void clickedOnFavoriteBusStopsInNavigationView();
        void clickedOnViewedBusStopsInNavigationView();
        void clickedOnBusRouteInNavigationView();
    }

}
