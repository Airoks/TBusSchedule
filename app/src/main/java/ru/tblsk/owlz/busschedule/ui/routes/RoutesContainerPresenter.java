package ru.tblsk.owlz.busschedule.ui.routes;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class RoutesContainerPresenter<V extends RoutesContainerMvpView>
        extends BasePresenter<V> implements RoutesContainerMvpPresenter<V>{

    @Inject
    public RoutesContainerPresenter(DataManager dataManager,
                                    CompositeDisposable compositeDisposable,
                                    SchedulerProvider schedulerProvider,
                                    RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider, eventBus);
    }
}
