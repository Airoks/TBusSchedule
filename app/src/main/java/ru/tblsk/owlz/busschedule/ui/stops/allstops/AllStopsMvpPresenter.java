package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface AllStopsMvpPresenter<V extends AllStopsMvpView> extends MvpPresenter<V> {
    void getAllStops();
}
