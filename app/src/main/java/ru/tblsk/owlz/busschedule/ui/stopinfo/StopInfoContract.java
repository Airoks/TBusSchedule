package ru.tblsk.owlz.busschedule.ui.stopinfo;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;

public interface StopInfoContract {

    interface View extends MvpView {
        void showDirectionsByStop(List<DirectionVO> directions);
        void showSavedDirectionsByStop();
        void openPreviousFragment();
        void openFavoritesDirectionsDialog();
        void openScheduleContainerFragment(FlightVO flight);
        void setFavoriteIcon(boolean isFavorite);
        void showSnackBarDeleted();
    }

    interface Presenter extends MvpPresenter<View> {
        void getDirectionsByStop(Long stopId, boolean isFavoriteStop);
        void getSavedDirectionsByStop();
        void clickedOnBackButton();
        void clickedOnButtonAddFavorites();
        void isFavoriteStop(Long stopId);
        void deleteFavoriteStop(Long stopId);
        void setClickListenerForAdapter();
    }

}
