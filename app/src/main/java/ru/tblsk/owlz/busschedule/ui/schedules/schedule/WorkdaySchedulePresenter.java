package ru.tblsk.owlz.busschedule.ui.schedules.schedule;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class WorkdaySchedulePresenter extends BasePresenter<ScheduleContract.View>
        implements ScheduleContract.Presenter {

    private List<DepartureTimeVO> mScheduleWorkday;
    private DepartureTimeMapper mDepartureTimeMapper;

    @Inject
    public WorkdaySchedulePresenter(DataManager dataManager,
                                    CompositeDisposable compositeDisposable,
                                    SchedulerProvider schedulerProvider,
                                    DepartureTimeMapper mapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mDepartureTimeMapper = mapper;
    }

    @Override
    public void getSchedule(long stopId, long directionId, int scheduleType) {
        if(mScheduleWorkday != null && !mScheduleWorkday.isEmpty()) {
            getMvpView().showSchedule(mScheduleWorkday);
        } else {
            getCompositeDisposable().add(getDataManager().getSchedule(stopId, directionId, scheduleType)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().io())
                    .map(mDepartureTimeMapper)
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<List<DepartureTimeVO>>() {
                        @Override
                        public void accept(List<DepartureTimeVO> departureTimes) throws Exception {
                            mScheduleWorkday = departureTimes;
                            if(departureTimes.isEmpty()) {
                                getMvpView().showEmptyScreen();
                            } else {
                                getMvpView().showSchedule(mScheduleWorkday);
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
    public void clearData() {
        mScheduleWorkday = null;
    }
}
