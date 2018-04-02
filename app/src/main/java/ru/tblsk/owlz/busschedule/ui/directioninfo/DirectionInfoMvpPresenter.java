package ru.tblsk.owlz.busschedule.ui.directioninfo;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;

public interface DirectionInfoMvpPresenter<V extends DirectionInfoMvpView>
        extends MvpPresenter<V>{

    void getStopsOnDirection(Long directionId);
    void getSavedStopsOnDirection();
    void clickedOnChangeDirectionButton(FlightVO flight);
    void clickedOnBackButton();
}
