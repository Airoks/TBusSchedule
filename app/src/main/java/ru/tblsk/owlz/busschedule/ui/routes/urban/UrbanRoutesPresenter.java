package ru.tblsk.owlz.busschedule.ui.routes.urban;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class UrbanRoutesPresenter<V extends UrbanRoutesMvpView>
        extends BasePresenter<V> implements UrbanRoutesMvpPresenter<V>{

    private RxEventBus mEventBus;
    private FlightMapper mFlightMapper;

    @Inject
    public UrbanRoutesPresenter(DataManager dataManager,
                                CompositeDisposable compositeDisposable,
                                SchedulerProvider schedulerProvider,
                                RxEventBus eventBus,
                                FlightMapper flightMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mEventBus = eventBus;
        this.mFlightMapper = flightMapper;
    }

    @Override
    public void getUrbanFlights() {
        getCompositeDisposable().add(getDataManager()
                .getFlightByType(FlightType.URBAN)
                .map(mFlightMapper)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<FlightVO>>() {
                    @Override
                    public void accept(List<FlightVO> flights) throws Exception {
                        getMvpView().showUrbanRoutes(flights);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));

    }

    @Override
    public void getSavedUrbanFlights() {
        getMvpView().showSavedUrbanRoutes();
    }

    @Override
    public void subscribeOnEvents() {
        //если сработал clear, то подписываемся
        if(getCompositeDisposable().size() == 0) {
            //clicked on change direction button in DirectionInfoFragment
            getCompositeDisposable().add(mEventBus.filteredObservable(ChangeDirectionUrban.InFragment.class)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<ChangeDirectionUrban.InFragment>() {
                        @Override
                        public void accept(ChangeDirectionUrban.InFragment inFragment) throws Exception {
                            getMvpView().changeDirection(inFragment.getPosition(), inFragment.getDirectionType());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));

            //clicked on change direction button
            getCompositeDisposable().add(mEventBus.filteredObservable(ChangeDirectionUrban.InAdapter.class)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<ChangeDirectionUrban.InAdapter>() {
                        @Override
                        public void accept(ChangeDirectionUrban.InAdapter inAdapter) throws Exception {
                            getMvpView().changeDirection(inAdapter.getPosition(), inAdapter.getDirectionType());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));

            //clicked on item recycler view
            getCompositeDisposable().add(mEventBus.filteredObservable(ChangeDirectionUrban.class)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<ChangeDirectionUrban>() {
                        @Override
                        public void accept(ChangeDirectionUrban directionUrban) throws Exception {
                            getMvpView().openDirectionInfoFragment(directionUrban.getFlightPosition());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));
        }
    }
}
