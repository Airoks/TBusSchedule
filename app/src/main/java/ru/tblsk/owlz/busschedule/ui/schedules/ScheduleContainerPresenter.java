package ru.tblsk.owlz.busschedule.ui.schedules;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class ScheduleContainerPresenter extends BasePresenter<ScheduleContainerContract.View>
        implements ScheduleContainerContract.Presenter{

    @Inject
    public ScheduleContainerPresenter(DataManager dataManager,
                                      CompositeDisposable compositeDisposable,
                                      SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }
}
