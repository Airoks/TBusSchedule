package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.base.TimeUntilNextFlights;
import ru.tblsk.owlz.busschedule.utils.NextFlight;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;

public interface BusStopInfoContract {

    interface View extends MvpView {
        void showDirectionsByStop(List<DirectionVO> directions);
        void showTimeOfNextFlight(List<NextFlight> nextFlights);
        void openPreviousFragment();
        void openFavoritesDirectionsDialog(List<DirectionVO> directions);
        void openScheduleContainerFragment(FlightVO flight);
        void setFavoriteIcon(boolean isFavorite);
        void showSnackBarDeleted();
        void showSnackBarAdded(boolean isFavoriteStop);
        void showSnackBarNotSelected();
    }

    interface Presenter extends MvpPresenter<View>, TimeUntilNextFlights {
        void getDirectionsByStop(Long stopId);
        void clickedOnBackButton();
        void clickedOnButtonAddFavorites();
        void isFavoriteStop(Long stopId);
        void deleteFavoriteStop(Long stopId);
        void clickedOnAdapterItem(long directionId, int directionType);
        void setClickListenerOnAddButtonInDialog();
        void cancelTimer();
        void startTimer();
    }

}
