package ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.BusScheduleScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@BusScheduleScreen
public class WeekendSchedulePresenter extends BasePresenter<BusScheduleContract.View>
        implements BusScheduleContract.Presenter {

    private DepartureTimeVO mScheduleWeekend;
    private DepartureTimeMapper mDepartureTimeMapper;

    @Inject
    public WeekendSchedulePresenter(DataManager dataManager,
                                    CompositeDisposable compositeDisposable,
                                    SchedulerProvider schedulerProvider,
                                    DepartureTimeMapper mapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mDepartureTimeMapper = mapper;
    }

    @Override
    public void getSchedule(long stopId, long directionId, int scheduleType) {
        if(mScheduleWeekend != null) {
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
}
