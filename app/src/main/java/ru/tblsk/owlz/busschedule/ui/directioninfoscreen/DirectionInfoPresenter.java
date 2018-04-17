package ru.tblsk.owlz.busschedule.ui.directioninfoscreen;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class DirectionInfoPresenter extends BasePresenter<DirectionInfoContract.View>
        implements DirectionInfoContract.Presenter{

    private static final int DIRECT = 0;
    private static final int REVERSE = 1;

    private StopMapper mStopMapper;
    private FlightVO mFlight;
    private List<StopVO> mStops;

    @Inject
    public DirectionInfoPresenter(DataManager dataManager,
                                  CompositeDisposable compositeDisposable,
                                  SchedulerProvider schedulerProvider,
                                  StopMapper stopMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        mStopMapper = stopMapper;
    }

    @Override
    public void getStopsOnDirection() {
        getMvpView().setToolbarTitle(mFlight.getFlightNumber());
        if(mStops != null) {
            getMvpView().showStopsOnDirection(mStops);
            getMvpView().setDirectionTitle(mFlight.getCurrentDirection().getDirectionName());
        } else {
           updateStops();
        }
    }

    @Override
    public void clickedOnChangeDirectionButton() {
        if(mFlight.getCurrentDirectionType() == DIRECT) {
            mFlight.setCurrentDirectionType(REVERSE);
        } else {
            mFlight.setCurrentDirectionType(DIRECT);
        }
        updateStops();
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }

    @Override
    public void setData(FlightVO flight) {
        if(mFlight == null) {
            mFlight = flight;
        }
    }

    @Override
    public void setChangeButton() {
        boolean flag = mFlight.getDirections().size() > 1;
        getMvpView().showChangeButton(flag);
    }

    @Override
    public void clickedOnAdapterItem(int position) {
        StopVO stop = mStops.get(position);
        getMvpView().openScheduleContainerFragment(stop, mFlight);
    }

    private void updateStops() {
        long directionId = mFlight.getCurrentDirection().getId();
        getCompositeDisposable().add(getDataManager().getStopsOnDirection(directionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .map(mStopMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<StopVO>>() {
                    @Override
                    public void accept(List<StopVO> stops) throws Exception {
                        mStops = stops;
                        getMvpView().showStopsOnDirection(stops);
                        getMvpView().setDirectionTitle(mFlight.getCurrentDirection().getDirectionName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
    }
