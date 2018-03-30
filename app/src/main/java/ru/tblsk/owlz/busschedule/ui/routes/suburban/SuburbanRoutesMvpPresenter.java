package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface SuburbanRoutesMvpPresenter<V extends SuburbanRoutesMvpView>
        extends MvpPresenter<V>{
    void getSuburbanFlights();
    void subscribeOnEvents();
}
