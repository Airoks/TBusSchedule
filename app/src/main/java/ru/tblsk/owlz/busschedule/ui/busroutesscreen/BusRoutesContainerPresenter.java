package ru.tblsk.owlz.busschedule.ui.busroutesscreen;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.BusRoutesScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@BusRoutesScreen
public class BusRoutesContainerPresenter extends BasePresenter<BusRoutesContainerContract.View>
        implements BusRoutesContainerContract.Presenter{

    @Inject
    public BusRoutesContainerPresenter(DataManager dataManager,
                                       CompositeDisposable compositeDisposable,
                                       SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void clickedOnNavigation() {
        getMvpView().openNavigationDrawer();
    }
}
