package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class AllStopsPresenter<V extends AllStopsMvpView> extends BasePresenter<V>
        implements AllStopsMvpPresenter<V>{

    private StopMapper mStopMapper;

    @Inject
    public AllStopsPresenter(DataManager dataManager,
                             CompositeDisposable compositeDisposable,
                             SchedulerProvider schedulerProvider,
                             StopMapper stopMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mStopMapper = stopMapper;
    }

    @Override
    public void getAllStops() {
        getCompositeDisposable().add(getDataManager()
                .getAllStops()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .map(mStopMapper)
                .subscribe(new Consumer<List<StopVO>>() {
                    @Override
                    public void accept(List<StopVO> stops) throws Exception {
                        getMvpView().showAllStops(stops);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    @Override
    public void getSavedAllStops() {
        getMvpView().showSavedAllStops();
    }

    @Override
    public void insertSearchHistoryStops(long stopId) {
        getCompositeDisposable().add(getDataManager()
                .insertSearchHistoryStops(stopId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }
}
