package ru.tblsk.owlz.busschedule.ui.schedules.schedule;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.data.db.model.ScheduleType;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class SchedulePresenter<V extends ScheduleMvpView>
        extends BasePresenter<V> implements ScheduleMvpPresenter<V> {

    private List<DepartureTime> mScheduleWorkday;
    private List<DepartureTime> mScheduleWeekend;

    @Inject
    public SchedulePresenter(DataManager dataManager,
                             CompositeDisposable compositeDisposable,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getSchedule(long stopId, long directionId, int scheduleType) {
        if(scheduleType == ScheduleType.WORKDAY.id) {
            if(mScheduleWorkday != null) {
                getMvpView().showSchedule(mScheduleWorkday);
            } else {
                getCompositeDisposable().add(getDataManager().getSchedule(stopId, directionId, scheduleType)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<DepartureTime>>() {
                            @Override
                            public void accept(List<DepartureTime> departureTimes) throws Exception {
                                mScheduleWorkday = departureTimes;
                                getMvpView().showSchedule(mScheduleWorkday);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }));
            }
        } else {
            if(mScheduleWeekend != null) {
                getMvpView().showSchedule(mScheduleWeekend);
            } else {
                getCompositeDisposable().add(getDataManager().getSchedule(stopId, directionId, scheduleType)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<DepartureTime>>() {
                            @Override
                            public void accept(List<DepartureTime> departureTimes) throws Exception {
                                mScheduleWeekend = departureTimes;
                                getMvpView().showSchedule(mScheduleWeekend);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }));
            }
        }

    }

    @Override
    public void clearData() {
        mScheduleWorkday = null;
        mScheduleWeekend = null;
    }
}
