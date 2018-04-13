package ru.tblsk.owlz.busschedule.ui.favorites;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class FavoriteStopsPresenter
        extends BasePresenter<FavoriteStopsContract.View> implements FavoriteStopsContract.Presenter {

    private StopMapper mStopMapper;
    private Disposable mDisposable;
    private RxEventBus mEventBus;

    @Inject
    public FavoriteStopsPresenter(DataManager dataManager,
                                  CompositeDisposable compositeDisposable,
                                  SchedulerProvider schedulerProvider,
                                  StopMapper mapper,
                                  RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mStopMapper = mapper;
        this.mEventBus = eventBus;
    }

    @Override
    public void getFavoriteStops() {
        getCompositeDisposable().add(getDataManager().getFavoriteStop()
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
                            getMvpView().showFavoriteStops(stops);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void setClickListenerForAdapter() {
        if(mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        mDisposable = mEventBus.filteredObservable(StopVO.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<StopVO>() {
                    @Override
                    public void accept(StopVO stopVO) throws Exception {
                        getMvpView().openStopInfoFragment(stopVO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    @Override
    public void clickedOnNavigation() {
        getMvpView().openNavigationDrawer();
    }


    @Override
    public void detachView() {
        if(mDisposable != null) {
            mDisposable.dispose();
        }
        super.detachView();
    }

}
