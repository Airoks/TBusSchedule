package ru.tblsk.owlz.busschedule.ui.stopinfo;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface StopInfoMvpPresenter<V extends StopInfoMvpView>
        extends MvpPresenter<V>{
    void getDirectionsByStop(Long stopId);
    void getSavedDirectionsByStop();
    void clickedOnBackButton();
    void clickedOnButtonAddFavorites();
    void isFavoriteStop(Long stopId);
    void deleteFavoriteStop(Long stopId);
    void setClickListenerForAdapter();
}
