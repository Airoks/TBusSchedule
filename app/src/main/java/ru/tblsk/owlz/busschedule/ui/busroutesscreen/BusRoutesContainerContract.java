package ru.tblsk.owlz.busschedule.ui.busroutesscreen;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface BusRoutesContainerContract {

    interface View extends MvpView {
        void openNavigationDrawer();
    }

    interface Presenter extends MvpPresenter<View> {
        void clickedOnNavigation();
    }
}
