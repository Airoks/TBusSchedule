package ru.tblsk.owlz.busschedule.ui.stops.historystops;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;


public class StopsPresenter<V extends StopsMvpView> extends BasePresenter<V>
        implements StopsMvpPresenter<V> {

    @Inject
    public StopsPresenter(DataManager dataManager,
                          CompositeDisposable compositeDisposable,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getSearchHistoryStops() {
        getCompositeDisposable().add(getDataManager()
                .getSearchHistoryStops()
                .subscribeOn(Schedulers.io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Stop>>() {
                    @Override
                    public void accept(List<Stop> stops) throws Exception {
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
                        getMvpView().showSearchHistoryStops(Collections.<Stop>emptyList());
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
}
