package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class AllStopsPresenter extends BasePresenter<AllStopsContract.View>
        implements AllStopsContract.Presenter{

    private StopMapper mStopMapper;
    private List<StopVO> mStops;

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
        if(mStops != null && !mStops.isEmpty()) {
            getMvpView().showAllStops(mStops);
        } else {
            getStops();
        }
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

    @Override
    public void clickedOnAdapterItem(int position) {
        getMvpView().openStopInfoFragment(mStops.get(position));
        insertSearchHistoryStops(mStops.get(position).getId());
    }

    @Override
    public void clearData() {
        mStops.clear();
        mStops = null;
    }

    private void getStops() {
        getCompositeDisposable().add(getDataManager()
                .getAllStops()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .map(mStopMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<StopVO>>() {
                    @Override
                    public void accept(List<StopVO> stops) throws Exception {
                        if(stops.isEmpty()) {
                            getMvpView().showEmptyScreen();
                        } else {
                            mStops = stops;
                            getMvpView().showAllStops(stops);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }
}
