package ru.tblsk.owlz.busschedule.ui.main;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter {

    @Inject
    public MainPresenter(DataManager dataManager,
                         CompositeDisposable compositeDisposable,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void clickedOnFavoriteBusStopsInNavigationView() {
        getMvpView().openFavoriteBusStopsFragment();
    }

    @Override
    public void clickedOnViewedBusStopsInNavigationView() {
        getMvpView().openViewedBusStopsFragment();
    }

    @Override
    public void clickedOnBusRouteInNavigationView() {
        getMvpView().openBusRoutesContainerFragment();
    }
}
