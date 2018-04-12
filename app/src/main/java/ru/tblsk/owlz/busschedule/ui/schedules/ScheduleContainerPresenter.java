package ru.tblsk.owlz.busschedule.ui.schedules;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class ScheduleContainerPresenter<V extends ScheduleContainerMvpView>
        extends BasePresenter<V> implements ScheduleContainerMvpPresenter<V>{

    @Inject
    public ScheduleContainerPresenter(DataManager dataManager,
                                      CompositeDisposable compositeDisposable,
                                      SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }
}
