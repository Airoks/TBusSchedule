package ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;

public interface BusRoutesContract {

    interface View extends MvpView {
        void showRoutes(List<FlightVO> flights);
        void showEmptyScreen();
        void openDirectionInfoFragment(FlightVO flight);
    }

    interface Presenter extends MvpPresenter<View> {
        void getFlights();
        void subscribeOnEvents();
        void clickedOnAdapterItem(int position);
        void clickedOnDirectionChangeButton(int position, int directionType);
    }

}
