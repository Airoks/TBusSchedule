package ru.tblsk.owlz.busschedule.ui.routes.urban;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface UrbanRoutesMvpPresenter<V extends UrbanRoutesMvpView>
        extends MvpPresenter<V>{
    void getUrbanFlights();
    void subscribeOnEvents();
}
