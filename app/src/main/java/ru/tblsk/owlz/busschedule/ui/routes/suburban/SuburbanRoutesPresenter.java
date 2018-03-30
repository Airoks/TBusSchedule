package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class SuburbanRoutesPresenter<V extends SuburbanRoutesMvpView>
        extends BasePresenter<V> implements SuburbanRoutesMvpPresenter<V> {


    @Inject
    public SuburbanRoutesPresenter(DataManager dataManager,
                                   CompositeDisposable compositeDisposable,
                                   SchedulerProvider schedulerProvider,
                                   RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider, eventBus);
    }

    @Override
    public void getSuburbanFlights() {
        getCompositeDisposable().add(getDataManager()
                .getFlightByType(FlightType.SUBURBAN)
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

    @Override
    public void subscribeOnEvents() {
        getCompositeDisposable().add(getEventBus().filteredObservable(ChangeDirectionSuburban.InFragment.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ChangeDirectionSuburban.InFragment>() {
                    @Override
                    public void accept(ChangeDirectionSuburban.InFragment inFragment) throws Exception {
                        getMvpView().changedDirectionInFragment(inFragment);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
        getCompositeDisposable().add(getEventBus().filteredObservable(ChangeDirectionSuburban.InAdapter.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ChangeDirectionSuburban.InAdapter>() {
                    @Override
                    public void accept(ChangeDirectionSuburban.InAdapter inAdapter) throws Exception {
                        getMvpView().changedDirectionInAdapter(inAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));

        getCompositeDisposable().add(getEventBus().filteredObservable(ChangeDirectionSuburban.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ChangeDirectionSuburban>() {
                    @Override
                    public void accept(ChangeDirectionSuburban directionSuburban) throws Exception {
                        getMvpView().openDirectionInfoFragment(directionSuburban);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
}
