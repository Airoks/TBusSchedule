package ru.tblsk.owlz.busschedule.ui.main.stops;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface StopsMvpPresenter<V extends StopsMvpView>
        extends MvpPresenter<V>{
    void getStops();
}
