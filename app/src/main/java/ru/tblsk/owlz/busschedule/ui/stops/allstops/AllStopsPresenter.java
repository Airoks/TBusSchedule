package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.stops.SelectedStop;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class AllStopsPresenter extends BasePresenter<AllStopsContract.View>
        implements AllStopsContract.Presenter{

    private StopMapper mStopMapper;
    private RxEventBus mEventBus;
    private Disposable mDisposable;

    @Inject
    public AllStopsPresenter(DataManager dataManager,
                             CompositeDisposable compositeDisposable,
                             SchedulerProvider schedulerProvider,
                             StopMapper stopMapper,
                             RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mStopMapper = stopMapper;
        this.mEventBus = eventBus;
    }

    @Override
    public void getAllStops() {
        getCompositeDisposable().add(getDataManager()
                .getAllStops()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .map(mStopMapper)
                .observeOn(getSchedulerProvider().ui())
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

    @Override
    public void setClickListenerForAdapter() {
        if(mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        mDisposable = mEventBus.filteredObservable(SelectedStop.InAllStops.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SelectedStop.InAllStops>() {
                    @Override
                    public void accept(SelectedStop.InAllStops inAllStops) throws Exception {
                        getMvpView().openStopInfoFragment(inAllStops.getStop());
                        insertSearchHistoryStops(inAllStops.getStop().getId());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void detachView() {
        if(mDisposable != null) {
            mDisposable.dispose();
        }
        super.detachView();
    }
}
