package ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class WeekendSchedulePresenter extends BasePresenter<BusScheduleContract.View>
        implements BusScheduleContract.Presenter {

    private List<DepartureTimeVO> mScheduleWeekend;
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
        if(mScheduleWeekend != null && !mScheduleWeekend.isEmpty()) {
            getMvpView().showSchedule(mScheduleWeekend);
        } else {
            getCompositeDisposable().add(getDataManager().getSchedule(stopId, directionId, scheduleType)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().io())
                    .map(mDepartureTimeMapper)
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<List<DepartureTimeVO>>() {
                        @Override
                        public void accept(List<DepartureTimeVO> departureTimes) throws Exception {
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
