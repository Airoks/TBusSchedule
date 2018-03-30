package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.util.Log;

import java.util.ArrayList;
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

public class UrbanRoutesPresenter<V extends UrbanRoutesMvpView>
        extends BasePresenter<V> implements UrbanRoutesMvpPresenter<V>{

    private List<ChangeDirectionUrban.InFragment> changeInFragment;

    @Inject
    public UrbanRoutesPresenter(DataManager dataManager,
                                CompositeDisposable compositeDisposable,
                                SchedulerProvider schedulerProvider,
                                RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider, eventBus);

        changeInFragment = new ArrayList<>();
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
        getMvpView().changedDirectionInFragment(changeInFragment);
        changeInFragment.clear();
    }

    @Override
    public void getUrbanFlights() {
        getCompositeDisposable().add(getDataManager()
                .getFlightByType(FlightType.URBAN)
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
    public void subscribeOnEvents() {
        getCompositeDisposable().add(getEventBus().filteredObservable(ChangeDirectionUrban.InFragment.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ChangeDirectionUrban.InFragment>() {
                    @Override
                    public void accept(ChangeDirectionUrban.InFragment inFragment) throws Exception {
                        changeInFragment.add(inFragment);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
        getCompositeDisposable().add(getEventBus().filteredObservable(ChangeDirectionUrban.InAdapter.class)
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
                }));

        getCompositeDisposable().add(getEventBus().filteredObservable(ChangeDirectionUrban.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ChangeDirectionUrban>() {
                    @Override
                    public void accept(ChangeDirectionUrban directionUrban) throws Exception {
                        getMvpView().openDirectionInfoFragment(directionUrban);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
}
