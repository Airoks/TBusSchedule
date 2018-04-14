package ru.tblsk.owlz.busschedule.ui.favorites;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class FavoriteStopsPresenter extends BasePresenter<FavoriteStopsContract.View>
        implements FavoriteStopsContract.Presenter {

    private StopMapper mStopMapper;
    private List<StopVO> mStops;

    @Inject
    public FavoriteStopsPresenter(DataManager dataManager,
                                  CompositeDisposable compositeDisposable,
                                  SchedulerProvider schedulerProvider,
                                  StopMapper mapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mStopMapper = mapper;
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
                            mStops = stops;
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
    public void clickedOnNavigation() {
        getMvpView().openNavigationDrawer();
    }

    @Override
    public void clickedOnAdapterItem(int position) {
        getMvpView().openStopInfoFragment(mStops.get(position));
    }

}
