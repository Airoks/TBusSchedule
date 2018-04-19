package ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class AllBusStopsPresenter extends BasePresenter<AllBusStopsContract.View>
        implements AllBusStopsContract.Presenter{

    private StopMapper mStopMapper;
    private List<StopVO> mStops;
    private String mSearchQuery;

    @Inject
    public AllBusStopsPresenter(DataManager dataManager,
                                CompositeDisposable compositeDisposable,
                                SchedulerProvider schedulerProvider,
                                StopMapper stopMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        mStopMapper = stopMapper;
        mSearchQuery = "";
    }

    @Override
    public void getAllStops() {
        if(mStops != null && !mStops.isEmpty()) {
            getMvpView().showAllStops(mStops);
            if(!mSearchQuery.isEmpty()) {
                getMvpView().showSearchResults(mSearchQuery);
            }
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
    public void clickedOnAdapterItem(long stopId) {
        StopVO stop = searchBusStopById(stopId);
        getMvpView().openStopInfoFragment(stop);
        mSearchQuery = "";
        insertSearchHistoryStops(stopId);
    }

    @Override
    public void searchBusStops(String text) {
        mSearchQuery = text;
        getMvpView().showSearchResults(text);
    }

    @Override
    public void searchQueryIsEmpty() {
        if(mSearchQuery.isEmpty()) {
            getMvpView().closeSearchView();
        }
    }

    private void getStops() {
        getCompositeDisposable().add(getDataManager()
                .getAllStops()
                .subscribeOn(getSchedulerProvider().io())
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

    private StopVO searchBusStopById(long id) {
        for(StopVO stop : mStops) {
            if(stop.getId() == id) {
                return stop;
            }
        }
        return null;
    }
}
