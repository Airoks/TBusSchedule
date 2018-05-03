package ru.tblsk.owlz.busschedule.ui.directioninfoscreen;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.screens.directioninfo.DirectionInfoScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.NextFlight;
import ru.tblsk.owlz.busschedule.utils.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@DirectionInfoScreen
public class DirectionInfoPresenter extends BasePresenter<DirectionInfoContract.View>
        implements DirectionInfoContract.Presenter {

    private static final int DIRECT = 0;
    private static final int REVERSE = 1;
    private static final int WORKDAY = 0;
    private static final int WEEKEND = 1;

    private StopMapper mStopMapper;
    private DepartureTimeMapper mTimeMapper;
    private FlightVO mFlight;
    private List<StopVO> mStops;
    private List<DepartureTimeVO> mSchedule;
    private List<NextFlight> mNextFlights;

    @Inject
    public DirectionInfoPresenter(DataManager dataManager,
                                  CompositeDisposable compositeDisposable,
                                  SchedulerProvider schedulerProvider,
                                  StopMapper stopMapper,
                                  DepartureTimeMapper departureTimeMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        mStopMapper = stopMapper;
        mTimeMapper = departureTimeMapper;
        mSchedule = new ArrayList<>();
        mNextFlights = new ArrayList<>();
    }

    @Override
    public void getStopsOnDirection(FlightVO flight) {
        mFlight = flight;
        getMvpView().setToolbarTitle(mFlight.getFlightNumber());
        if(mStops != null) {
            getMvpView().showStopsOnDirection(mStops);
            getMvpView().setDirectionTitle(mFlight.getCurrentDirection().getDirectionName());
            setTimer();
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
                        getMvpView().showStopsOnDirection(stops);
                        getSchedule(directionId);
                        getMvpView().setDirectionTitle(mFlight.getCurrentDirection().getDirectionName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void getSchedule(long directionId) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
            getScheduleByType(directionId, WEEKEND);
        } else {
            getScheduleByType(directionId, WORKDAY);
        }
    }

    @Override
    public void getScheduleByType(long directionId, int scheduleType) {
        mSchedule.clear();
        mNextFlights.clear();
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
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        setTimer();
                    }
                }));

    }

    @Override
    public void setTimer() {
        mNextFlights.clear();
        for(int i = 0; i < mStops.size(); i ++) {
            getNextFlight(i, false);
        }

        getMvpView().showTimeOfNextFlight(mNextFlights);

        getCompositeDisposable().clear();
        getCompositeDisposable().add(Observable.interval(1, TimeUnit.MINUTES)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        for(int i = 0; i < mNextFlights.size(); i ++) {
                            if(mNextFlights.get(i).isInitialized()) {
                                int timeBefore = mNextFlights.get(i).getTimeBeforeDeparture() - 1;
                                NextFlight next = newNextFlight(mNextFlights.get(i));
                                mNextFlights.set(i, next);
                                if(timeBefore < 0) {
                                    getNextFlight(i, true);
                                }
                            }
                        }
                        getMvpView().showTimeOfNextFlight(mNextFlights);
                    }
                }));
    }

    @Override
    public NextFlight newNextFlight(NextFlight old) {
        NextFlight next = new NextFlight();
        next.setHour(old.getHour());
        next.setMinute(old.getMinute());
        next.setTimeBeforeDeparture(old.getTimeBeforeDeparture() - 1);
        next.setInitialized(true);
        return next;
    }

    @Override
    public void getNextFlight(int position, boolean set) {
        DepartureTimeVO schedule = mSchedule.get(position);
        NextFlight nextFlight = new NextFlight();
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        for(int hour : schedule.getHours()) {
            if(currentHour == hour) {
                for(int minute : schedule.getTime().get(hour)) {
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    if(currentMinute <= minute) {
                        nextFlight.setHour(hour);
                        nextFlight.setMinute(minute);
                        int timeBefore = minute - currentMinute;
                        nextFlight.setTimeBeforeDeparture(timeBefore);
                        nextFlight.setInitialized(true);
                        if(set) {
                            mNextFlights.set(position, nextFlight);
                        } else {
                            mNextFlights.add(nextFlight);
                        }
                        return;
                    }
                }
            } else if(currentHour < hour) {
                int currentMinute = calendar.get(Calendar.MINUTE);
                int minute = schedule.getTime().get(hour).get(0);

                nextFlight.setHour(hour);
                nextFlight.setMinute(minute);
                int timeBefore = (hour - currentHour)* 60 + (minute - currentMinute);
                nextFlight.setTimeBeforeDeparture(timeBefore);
                nextFlight.setInitialized(true);
                if(set) {
                    mNextFlights.set(position, nextFlight);
                } else {
                    mNextFlights.add(nextFlight);
                }
                return;
            }
        }
        mNextFlights.add(nextFlight);
    }



    }
