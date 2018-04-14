package ru.tblsk.owlz.busschedule.ui.routes;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface RoutesContainerContract {

    interface View extends MvpView {
    }

    interface Presenter extends MvpPresenter<View> {
    }
}
