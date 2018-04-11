package ru.tblsk.owlz.busschedule.ui.directioninfo;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;

public interface DirectionInfoMvpPresenter<V extends DirectionInfoMvpView>
        extends MvpPresenter<V>{

    void getStopsOnDirection();
    void clickedOnChangeDirectionButton();
    void clickedOnBackButton();
    void clearData();
    void setData(FlightVO flight);
    void setChangeButton();
}
