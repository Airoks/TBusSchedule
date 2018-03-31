package ru.tblsk.owlz.busschedule.ui.routes.suburban;


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

public class SuburbanRoutesPresenter<V extends SuburbanRoutesMvpView>
        extends BasePresenter<V> implements SuburbanRoutesMvpPresenter<V> {

    private RxEventBus mEventBus;
    private List<ChangeDirectionSuburban.InFragment> changeInFragment;

    @Inject
    public SuburbanRoutesPresenter(DataManager dataManager,
                                   CompositeDisposable compositeDisposable,
                                   SchedulerProvider schedulerProvider,
                                   RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mEventBus = eventBus;
        changeInFragment = new ArrayList<>();
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
        getMvpView().changedDirectionInFragment(changeInFragment);
        changeInFragment.clear();
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
        //clicked on change direction button in DirectionInfoFragment
        getCompositeDisposable().add(mEventBus.filteredObservable(ChangeDirectionSuburban.InFragment.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ChangeDirectionSuburban.InFragment>() {
                    @Override
                    public void accept(ChangeDirectionSuburban.InFragment inFragment) throws Exception {
                        changeInFragment.add(inFragment);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));

        //clicked on change direction button
        getCompositeDisposable().add(mEventBus.filteredObservable(ChangeDirectionSuburban.InAdapter.class)
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

        //clicked on item recycler view
        getCompositeDisposable().add(mEventBus.filteredObservable(ChangeDirectionSuburban.class)
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
