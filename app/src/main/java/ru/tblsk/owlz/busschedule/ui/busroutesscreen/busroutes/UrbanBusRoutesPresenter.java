package ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.BusRoutesScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@BusRoutesScreen
public class UrbanBusRoutesPresenter extends BasePresenter<BusRoutesContract.View>
        implements BusRoutesContract.Presenter {

    private static final int URBAN = 0;

    private FlightMapper mFlightMapper;
    private List<FlightVO> mFlights;

    @Inject
    public UrbanBusRoutesPresenter(DataManager dataManager,
                                   CompositeDisposable compositeDisposable,
                                   SchedulerProvider schedulerProvider,
                                   FlightMapper flightMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

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
    public void clickedOnAdapterItem(int position) {
        getMvpView().openDirectionInfoFragment(mFlights.get(position));
    }

    @Override
    public void clickedOnDirectionChangeButton(int position, int directionType) {
        changeDirection(position, directionType);
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
