package ru.tblsk.owlz.busschedule.ui.stopinfo;


import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface StopInfoMvpView extends MvpView{
    void showDirectionsByStop();
    void showSavedDirectionsByStop();
    void openPreviousFragment();
}
