package ru.tblsk.owlz.busschedule.ui.routes.urban;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class UrbanRoutesPresenter<V extends UrbanRoutesMvpView>
        extends BasePresenter<V> implements UrbanRoutesMvpPresenter<V>{

    private RxEventBus mEventBus;
    private static final String FLIGHT_TYPE = "urban";

    @Inject
    public UrbanRoutesPresenter(DataManager dataManager,
                                CompositeDisposable compositeDisposable,
                                SchedulerProvider schedulerProvider,
                                RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);
        mEventBus = eventBus;
    }

    @Override
    public void getUrbanFlights() {
        getCompositeDisposable().add(getDataManager()
                .getFlightByType(FLIGHT_TYPE)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Flight>>() {
                    @Override
                    public void accept(List<Flight> flights) throws Exception {
                        getMvpView().showUrbanRoutes(flights);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));

    }

    @Override
    public void changeDirectionFragment() {
        mEventBus.filteredObservable(ChangeDirectionUrban.InFragment.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ChangeDirectionUrban.InFragment>() {
                    @Override
                    public void accept(ChangeDirectionUrban.InFragment inFragment) throws Exception {
                        getMvpView().changedDirectionInFragment(inFragment);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void changeDirectionAdapter() {
        mEventBus.filteredObservable(ChangeDirectionUrban.InAdapter.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ChangeDirectionUrban.InAdapter>() {
                    @Override
                    public void accept(ChangeDirectionUrban.InAdapter inAdapter) throws Exception {
                        getMvpView().changedDirectionInAdapter(inAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
