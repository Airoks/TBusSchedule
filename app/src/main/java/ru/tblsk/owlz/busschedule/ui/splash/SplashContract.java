package ru.tblsk.owlz.busschedule.ui.splash;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface SplashContract {

    interface View extends MvpView {
        void openMainActivity();
    }

    interface Presenter extends MvpPresenter<View> {
    }

}
