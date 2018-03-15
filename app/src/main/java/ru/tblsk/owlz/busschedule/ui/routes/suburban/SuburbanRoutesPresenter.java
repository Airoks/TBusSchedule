package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class SuburbanRoutesPresenter<V extends SuburbanRoutesMvpView>
        extends BasePresenter<V> implements SuburbanRoutesMvpPresenter<V> {

    private static final String FLIGHT_TYPE = "suburban";

    @Inject
    public SuburbanRoutesPresenter(DataManager dataManager,
                                   CompositeDisposable compositeDisposable,
                                   SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getSuburbanFlights() {
        getCompositeDisposable().add(getDataManager()
                .getFlightByType(FLIGHT_TYPE)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Flight>>() {
                    @Override
                    public void accept(List<Flight> flights) throws Exception {
                        getMvpView().showSuburbanRoutes(flights);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
}
