package ru.tblsk.owlz.busschedule.ui.stops.viewedstops;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.stops.StopsEvent;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;


public class StopsPresenter extends BasePresenter<StopsContract.View>
        implements StopsContract.Presenter {

    private StopMapper mStopMapper;
    private RxEventBus mEventBus;

    @Inject
    public StopsPresenter(DataManager dataManager,
                          CompositeDisposable compositeDisposable,
                          SchedulerProvider schedulerProvider,
                          StopMapper stopMapper,
                          RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mStopMapper = stopMapper;
        this.mEventBus = eventBus;
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
                        //если что передадим пустой список
                        getMvpView().showSearchHistoryStops(stops);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String error = throwable.getMessage();
                        Log.d("getSearchHistoryStops: ", error);
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
                        Log.d("deleteSHistoryStops:", error);
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
    public void subscribeOnEvents() {
        if(getCompositeDisposable().size() == 0) {
            getCompositeDisposable().add(mEventBus.filteredObservable(StopsEvent.InViewedStops.class)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<StopsEvent.InViewedStops>() {
                        @Override
                        public void accept(StopsEvent.InViewedStops inViewedStops) throws Exception {
                            getMvpView().openStopInfoFragment(inViewedStops.getStop());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));
        }
    }
}