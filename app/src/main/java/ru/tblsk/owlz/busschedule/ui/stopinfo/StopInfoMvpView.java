package ru.tblsk.owlz.busschedule.ui.stopinfo;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public interface StopInfoMvpView extends MvpView{
    void showDirectionsByStop(List<DirectionVO> directions);
    void showSavedDirectionsByStop();
    void openPreviousFragment();
    void openFavoritesDirectionsDialog();
    void openScheduleContainerFragment(FlightVO flight);
    void setFavoriteIcon(boolean isFavorite);
    void showSnackBarDeleted();
}
