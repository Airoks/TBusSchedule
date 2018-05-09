package ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule;


import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.BusScheduleScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.NextFlight;
import ru.tblsk.owlz.busschedule.utils.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@BusScheduleScreen
public class WeekendSchedulePresenter extends BasePresenter<BusScheduleContract.View>
        implements BusScheduleContract.Presenter {

    private DepartureTimeVO mScheduleWeekend;
    private DepartureTimeMapper mDepartureTimeMapper;
    private NextFlight mNextFlight;
    private Disposable mTimerDisposable;
    private boolean mFirstStart;
    private int mCurrentHoursPosition;

    @Inject
    public WeekendSchedulePresenter(DataManager dataManager,
                                    CompositeDisposable compositeDisposable,
                                    SchedulerProvider schedulerProvider,
                                    DepartureTimeMapper mapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        mDepartureTimeMapper = mapper;
        mFirstStart = true;
    }

    @Override
    public void getSchedule(long stopId, long directionId, int scheduleType) {
        if(mScheduleWeekend != null && !mScheduleWeekend.isEmpty()) {
            getMvpView().showSchedule(mScheduleWeekend);
        } else {
            getCompositeDisposable().add(getDataManager().getSchedule(stopId, directionId, scheduleType)
                    .subscribeOn(getSchedulerProvider().io())
                    .map(mDepartureTimeMapper)
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<DepartureTimeVO>() {
                        @Override
                        public void accept(DepartureTimeVO departureTimes) throws Exception {
                            mScheduleWeekend = departureTimes;
                            if(departureTimes.isEmpty()) {
                                getMvpView().showEmptyScreen();
                            } else {
                                getMvpView().showSchedule(mScheduleWeekend);
                                setTimer();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    }));
        }
    }

    @Override
    public void cancelTimer() {
        if(mTimerDisposable != null && !mTimerDisposable.isDisposed()) {
            mTimerDisposable.dispose();
        }
    }

    @Override
    public void startTimer() {
        if(!mFirstStart) {
            setTimer();
        }
    }

    private void setTimer() {
        cancelTimer();
        getNextFlight();
        if(mNextFlight.isInitialized()) {
            getMvpView().setColorItem(mCurrentHoursPosition);
        } else {
            getMvpView().resetColorItem();
        }
        mFirstStart = false;

        mTimerDisposable = Observable.interval(1, TimeUnit.MINUTES)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if(mNextFlight.isInitialized()) {
                            int timeBefore = mNextFlight.getTimeBeforeDeparture() - 1;
                            mNextFlight.setTimeBeforeDeparture(timeBefore);
                            if(timeBefore < 0) {
                                getNextFlight();
                                if(mNextFlight.isInitialized()) {
                                    getMvpView().setColorItem(mCurrentHoursPosition);
                                }
                            }
                        } else {
                            getNextFlight();
                            if(mNextFlight.isInitialized()) {
                                getMvpView().setColorItem(mCurrentHoursPosition);
                            }
                        }
                    }
                });
    }

    private void getNextFlight() {
        int position = 0;
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        mNextFlight = new NextFlight();
        for(int hour : mScheduleWeekend.getHours()) {
            if(currentHour == hour) {
                for(int minute : mScheduleWeekend.getTime().get(hour)) {
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    if(currentMinute <= minute) {
                        mNextFlight.setHour(hour);
                        mNextFlight.setMinute(minute);
                        int timeBefore = minute - currentMinute;
                        mNextFlight.setTimeBeforeDeparture(timeBefore);
                        mNextFlight.setInitialized(true);
                        mCurrentHoursPosition = position;
                        return;
                    }
                }
            } else if(currentHour < hour) {
                int currentMinute = calendar.get(Calendar.MINUTE);
                int minute = mScheduleWeekend.getTime().get(hour).get(0);

                mNextFlight.setHour(hour);
                mNextFlight.setMinute(minute);
                int timeBefore = (hour - currentHour)* 60 + (minute - currentMinute);
                mNextFlight.setTimeBeforeDeparture(timeBefore);
                mNextFlight.setInitialized(true);
                mCurrentHoursPosition = position;
                return;
            }
            position ++;
        }
        mNextFlight = new NextFlight();
    }
}
