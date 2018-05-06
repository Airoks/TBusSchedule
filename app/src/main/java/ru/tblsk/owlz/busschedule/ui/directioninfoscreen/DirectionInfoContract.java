package ru.tblsk.owlz.busschedule.ui.directioninfoscreen;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.base.TimeUntilNextFlights;
import ru.tblsk.owlz.busschedule.utils.NextFlight;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;

public interface DirectionInfoContract {

    interface View extends MvpView {
        void showStopsOnDirection(List<StopVO> stops);
        void showTimeOfNextFlight(List<NextFlight> nextFlights);
        void openPreviousFragment();
        void openScheduleContainerFragment(StopVO stop, FlightVO flight);
        void setDirectionTitle(String directionName);
        void setToolbarTitle(String flightNumber);
        void showChangeButton(boolean flag);
    }

    interface Presenter extends MvpPresenter<View>, TimeUntilNextFlights {
        void getStopsOnDirection(FlightVO flight);
        void clickedOnChangeDirectionButton();
        void clickedOnBackButton();
        void setChangeButton();
        void clickedOnAdapterItem(int position);
        void cancelTimer();
        void startTimer();
    }

}
