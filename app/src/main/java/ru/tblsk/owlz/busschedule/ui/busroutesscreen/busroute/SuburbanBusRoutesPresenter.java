package ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroute;


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

public class SuburbanBusRoutesPresenter extends BasePresenter<BusRouteContract.View>
        implements  BusRouteContract.Presenter {

    private static final int SUBURBAN = 1;

    private RxEventBus mEventBus;
    private FlightMapper mFlightMapper;
    private List<FlightVO> mFlights;

    @Inject
    public SuburbanBusRoutesPresenter(DataManager dataManager,
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
           getCompositeDisposable().add(mEventBus.filteredObservable(SuburbanDirectionEvent.InFragment.class)
                   .subscribeOn(getSchedulerProvider().io())
                   .observeOn(getSchedulerProvider().ui())
                   .subscribe(new Consumer<SuburbanDirectionEvent.InFragment>() {
                       @Override
                       public void accept(SuburbanDirectionEvent.InFragment inFragment) throws Exception {
                           changeDirection(inFragment.getPosition(), inFragment.getDirectionType());
                       }
                   }, new Consumer<Throwable>() {
                       @Override
                       public void accept(Throwable throwable) throws Exception {

                       }
                   }));

           //clicked on change direction button
           getCompositeDisposable().add(mEventBus.filteredObservable(SuburbanDirectionEvent.InAdapter.class)
                   .subscribeOn(getSchedulerProvider().io())
                   .observeOn(getSchedulerProvider().ui())
                   .subscribe(new Consumer<SuburbanDirectionEvent.InAdapter>() {
                       @Override
                       public void accept(SuburbanDirectionEvent.InAdapter inAdapter) throws Exception {
                           changeDirection(inAdapter.getPosition(), inAdapter.getDirectionType());
                       }
                   }, new Consumer<Throwable>() {
                       @Override
                       public void accept(Throwable throwable) throws Exception {

                       }
                   }));

           //clicked on item recycler view
           getCompositeDisposable().add(mEventBus.filteredObservable(SuburbanDirectionEvent.class)
                   .subscribeOn(getSchedulerProvider().io())
                   .observeOn(getSchedulerProvider().ui())
                   .subscribe(new Consumer<SuburbanDirectionEvent>() {
                       @Override
                       public void accept(SuburbanDirectionEvent directionSuburban) throws Exception {
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

    private void updateFlights() {
        getCompositeDisposable().add(getDataManager()
                .getFlightByType(SUBURBAN)
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
