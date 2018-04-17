package ru.tblsk.owlz.busschedule.ui.base;



public interface MvpPresenter<V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
    void unsubscribe();
}
