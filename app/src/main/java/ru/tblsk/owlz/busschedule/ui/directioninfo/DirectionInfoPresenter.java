package ru.tblsk.owlz.busschedule.ui.directioninfo;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class DirectionInfoPresenter<V extends DirectionInfoMvpView> extends BasePresenter<V>
        implements DirectionInfoMvpPresenter<V>{

    @Inject
    public DirectionInfoPresenter(DataManager dataManager,
                                  CompositeDisposable compositeDisposable,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getStopsOnDirection(Long directionId) {
        getCompositeDisposable().add(getDataManager().getStopsOnDirection(directionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Stop>>() {
                    @Override
                    public void accept(List<Stop> stops) throws Exception {
                        getMvpView().showStopsOnDirection(stops);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
}
