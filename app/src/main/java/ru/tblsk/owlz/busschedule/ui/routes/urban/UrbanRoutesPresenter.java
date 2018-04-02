package ru.tblsk.owlz.busschedule.ui.routes.urban;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class UrbanRoutesPresenter<V extends UrbanRoutesMvpView>
        extends BasePresenter<V> implements UrbanRoutesMvpPresenter<V>{

    private RxEventBus mEventBus;
    private ChangeDirectionUrban.InFragment mChangeInFragment;

    @Inject
    public UrbanRoutesPresenter(DataManager dataManager,
                                CompositeDisposable compositeDisposable,
                                SchedulerProvider schedulerProvider,
                                RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mEventBus = eventBus;
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
        if(mChangeInFragment != null) {
            getMvpView().updateDirectionFromDirectionInfo(mChangeInFragment);
        }
        mChangeInFragment = null;
    }

    @Override
    public void getUrbanFlights() {
        getCompositeDisposable().add(getDataManager()
                .getFlightByType(FlightType.URBAN)
                .map(new Function<List<Flight>, List<FlightVO>>() {
                    @Override
                    public List<FlightVO> apply(List<Flight> flights) throws Exception {
                        List<FlightVO> flightVOList = new ArrayList<>();
                        for(int i = 0; i < flights.size(); i ++) {
                            FlightVO flightVO = new FlightVO();
                            flightVO.setId(flights.get(i).getId());
                            flightVO.setFlightNumber(flights.get(i).getFlightNumber());
                            flightVO.setFlightType(flights.get(i).getFlightType().id);
                            flightVO.setDirections(flights.get(i).getDirections());
                            flightVO.setPosition(i);
                            flightVO.setCurrentDirection(DirectionType.DIRECT.id);
                            flightVOList.add(flightVO);
                        }
                        return flightVOList;
                    }
                })
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
                            mChangeInFragment = inFragment;
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
                            getMvpView().updateDirectionFromAdapter(inAdapter);
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
                            getMvpView().openDirectionInfoFragment(directionUrban);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));
        }
    }
}
