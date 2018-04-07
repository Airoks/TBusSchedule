package ru.tblsk.owlz.busschedule.ui.stopinfo;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.viewobject.DirectionVO;

public interface StopInfoMvpView extends MvpView{
    void showDirectionsByStop(List<DirectionVO> directions);
    void showSavedDirectionsByStop();
    void openPreviousFragment();
    void openFavoritesDirectionsDialog();
    void setFavoriteIcon(boolean isFavorite);
}
