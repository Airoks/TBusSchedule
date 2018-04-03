package ru.tblsk.owlz.busschedule.ui.stopinfo;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class StopInfoPresenter<V extends StopInfoMvpView> extends BasePresenter<V>
        implements StopInfoMvpPresenter<V>{

    @Inject
    public StopInfoPresenter(DataManager dataManager,
                             CompositeDisposable compositeDisposable,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getDirectionsByStop(Long stopId) {

    }

    @Override
    public void getSavedDirectionsByStop() {
        getMvpView().showSavedDirectionsByStop();
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }
}
