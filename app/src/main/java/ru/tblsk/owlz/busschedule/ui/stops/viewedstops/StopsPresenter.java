package ru.tblsk.owlz.busschedule.ui.stops.viewedstops;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.annotation.ViewedBusStopsScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;


public class StopsPresenter extends BasePresenter<StopsContract.View>
        implements StopsContract.Presenter {

    private StopMapper mStopMapper;
    private List<StopVO> mStops;

    @Inject
    public StopsPresenter(DataManager dataManager,
                          CompositeDisposable compositeDisposable,
                          SchedulerProvider schedulerProvider,
                          StopMapper stopMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mStopMapper = stopMapper;
    }

    @Override
    public void getSearchHistoryStops() {
        getCompositeDisposable().add(getDataManager()
                .getSearchHistoryStops()
                .subscribeOn(Schedulers.io())
                .observeOn(getSchedulerProvider().io())
                .map(mStopMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<StopVO>>() {
                    @Override
                    public void accept(List<StopVO> stops) throws Exception {
                        mStops = stops;
                        getMvpView().showSearchHistoryStops(stops);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String error = throwable.getMessage();
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void deleteSearchHistoryStops() {
        getCompositeDisposable().add(getDataManager()
                .deleteSearchHistory()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().showSearchHistoryStops(Collections.<StopVO>emptyList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String error = throwable.getMessage();
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void clickedOnAllStopsButton() {
        getMvpView().showAllStopsFragment();
    }

    @Override
    public void clickedOnNavigation() {
        getMvpView().openNavigationDrawer();
    }

    @Override
    public void clickedOnAdapterItem(int position) {
        getMvpView().openStopInfoFragment(mStops.get(position));
    }

}
