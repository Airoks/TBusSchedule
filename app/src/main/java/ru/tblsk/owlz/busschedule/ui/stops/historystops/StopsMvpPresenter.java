package ru.tblsk.owlz.busschedule.ui.stops.historystops;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface StopsMvpPresenter<V extends StopsMvpView>
        extends MvpPresenter<V>{
    void getSearchHistoryStops();
    void deleteSearchHistoryStops();
}
