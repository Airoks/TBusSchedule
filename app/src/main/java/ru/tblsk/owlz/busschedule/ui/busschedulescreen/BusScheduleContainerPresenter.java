package ru.tblsk.owlz.busschedule.ui.busschedulescreen;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class BusScheduleContainerPresenter extends BasePresenter<BusScheduleContainerContract.View>
        implements BusScheduleContainerContract.Presenter{

    @Inject
    public BusScheduleContainerPresenter(DataManager dataManager,
                                         CompositeDisposable compositeDisposable,
                                         SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }
}
