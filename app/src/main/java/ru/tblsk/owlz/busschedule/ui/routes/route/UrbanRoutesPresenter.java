package ru.tblsk.owlz.busschedule.ui.routes.route;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class UrbanRoutesPresenter extends BasePresenter<RouteContract.View>
        implements RouteContract.Presenter {

    public static final int URBAN = 0;

    private RxEventBus mEventBus;
    private FlightMapper mFlightMapper;
    private List<FlightVO> mFlights;

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
    public void getFlights() {
        if(mFlights != null && !mFlights.isEmpty()) {
            getMvpView().showRoutes(mFlights);
        } else {
            updateFlights();
        }
    }

    @Override
    public void subscribeOnEvents() {
        //если сработал clear, то подписываемся
        if(getCompositeDisposable().size() == 0) {
            //clicked on change direction button in DirectionInfoFragment
            getCompositeDisposable().add(mEventBus.filteredObservable(UrbanDirectionEvent.InFragment.class)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<UrbanDirectionEvent.InFragment>() {
                        @Override
                        public void accept(UrbanDirectionEvent.InFragment inFragment) throws Exception {
                            changeDirection(inFragment.getPosition(), inFragment.getDirectionType());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));

            //clicked on change direction button
            getCompositeDisposable().add(mEventBus.filteredObservable(UrbanDirectionEvent.InAdapter.class)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<UrbanDirectionEvent.InAdapter>() {
                        @Override
                        public void accept(UrbanDirectionEvent.InAdapter inAdapter) throws Exception {
                            changeDirection(inAdapter.getPosition(), inAdapter.getDirectionType());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));

            //clicked on item recycler view
            getCompositeDisposable().add(mEventBus.filteredObservable(UrbanDirectionEvent.class)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<UrbanDirectionEvent>() {
                        @Override
                        public void accept(UrbanDirectionEvent directionSuburban) throws Exception {
                            int position = directionSuburban.getFlightPosition();
                            getMvpView().openDirectionInfoFragment(mFlights.get(position));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));
        }
    }

    @Override
    public void clearData() {
        mFlights.clear();
        mFlights = null;
    }

    private void updateFlights() {
        getCompositeDisposable().add(getDataManager()
                .getFlightByType(URBAN)
                .map(mFlightMapper)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<FlightVO>>() {
                    @Override
                    public void accept(List<FlightVO> flights) throws Exception {
                        if(flights.isEmpty()) {
                            getMvpView().showEmptyScreen();
                        } else {
                            mFlights = flights;
                            getMvpView().showRoutes(flights);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    private void changeDirection(int position, int directionType) {
        FlightVO flightVO = mFlights.get(position);
        flightVO.setCurrentDirectionType(directionType);
        mFlights.set(position, flightVO);
    }
}
