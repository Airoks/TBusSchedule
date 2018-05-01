package ru.tblsk.owlz.busschedule.ui.directioninfoscreen;


import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.data.db.model.Schedule;
import ru.tblsk.owlz.busschedule.di.screens.directioninfo.DirectionInfoScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

import static java.lang.Thread.currentThread;

@DirectionInfoScreen
public class DirectionInfoPresenter extends BasePresenter<DirectionInfoContract.View>
        implements DirectionInfoContract.Presenter{

    private static final int DIRECT = 0;
    private static final int REVERSE = 1;
    private static final int WORKDAY = 0;
    private static final int WEEKEND = 1;

    private StopMapper mStopMapper;
    private DepartureTimeMapper mTimeMapper;
    private FlightVO mFlight;
    private List<StopVO> mStops;
    private List<DepartureTimeVO> mSchedule;

    @Inject
    public DirectionInfoPresenter(DataManager dataManager,
                                  CompositeDisposable compositeDisposable,
                                  SchedulerProvider schedulerProvider,
                                  StopMapper stopMapper,
                                  DepartureTimeMapper departureTimeMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        mStopMapper = stopMapper;
        mTimeMapper = departureTimeMapper;
    }

    @Override
    public void getStopsOnDirection(FlightVO flight) {
        mFlight = flight;
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
        final long directionId = mFlight.getCurrentDirection().getId();
        getCompositeDisposable().add(getDataManager().getStopsOnDirection(directionId)
                .subscribeOn(getSchedulerProvider().io())
                .map(mStopMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<StopVO>>() {
                    @Override
                    public void accept(List<StopVO> stops) throws Exception {
                        mStops = stops;
                        //getMvpView().showStopsOnDirection(stops);
                        getSchedule(directionId);
                        getMvpView().setDirectionTitle(mFlight.getCurrentDirection().getDirectionName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    private List<Long> getStopId() {
        List<Long> stopId = new ArrayList<>();
        for(StopVO stop : mStops) {
            stopId.add(stop.getId());
        }
        return stopId;
    }

    private void getSchedule(long directionId) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
            getDepartureTime(directionId, WEEKEND);
        } else {
            getDepartureTime(directionId, WORKDAY);
        }
    }

    private void getDepartureTime(long directionId, int scheduleType) {
        mSchedule = new ArrayList<>();

        getCompositeDisposable().clear();
        getCompositeDisposable().add(getDataManager().getScheduleByDirection(directionId, scheduleType)
                .subscribeOn(getSchedulerProvider().io())
                .map(mTimeMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<DepartureTimeVO>() {
                    @Override
                    public void onNext(DepartureTimeVO time) {
                        mSchedule.add(time);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        for(int i = 0; i < mStops.size(); i ++) {
                            nextFlight(i);
                        }
                        Log.d("Thread", currentThread().getName());
                        getMvpView().showStopsOnDirection(mStops);
                    }
                }));

        /*mBusSchedule = new ArrayList<>();
        getCompositeDisposable().add(getDataManager().getScheduleByDirection(directionId, scheduleType)
                .subscribeOn(getSchedulerProvider().io())
                .map(mTimeMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<List<DepartureTimeVO>>() {
                    @Override
                    public void onNext(List<DepartureTimeVO> departureTime) {
                        mBusSchedule.add(departureTime);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        for(int i = 0; i < mStops.size(); i ++) {
                            nextFlight(i);
                        }
                        Log.d("Thread", currentThread().getName());
                        getMvpView().showStopsOnDirection(mStops);
                    }
                }));*/
    }

    private void nextFlight(int position) {
        DepartureTimeVO times = mSchedule.get(position);
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        for(int hour : times.getHours()) {
            if(currentHour == hour) {
                for(int minute : times.getMinute()) {
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    if(currentMinute <= minute) {
                        StopVO stop = mStops.get(position);
                        stop.setHour(hour);
                        stop.setMinute(minute);
                        stop.setTimeBeforeDeparture(minute - currentMinute);
                        mStops.set(position, stop);
                        return;
                    }
                }
            } else if(currentHour < hour) {
                int currentMinute = calendar.get(Calendar.MINUTE);
                int minute = times.getMinute().get(0);

                StopVO stop = mStops.get(position);
                stop.setHour(hour);
                stop.setMinute(minute);
                stop.setTimeBeforeDeparture(minute - currentMinute);
                mStops.set(position, stop);
                return;
            }
        }
    }

    }
