package ru.tblsk.owlz.busschedule.ui.favoritebusstopsscreen;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.FavoriteBusStopsScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@FavoriteBusStopsScreen
public class FavoriteBusStopsPresenter extends BasePresenter<FavoriteBusStopsContract.View>
        implements FavoriteBusStopsContract.Presenter {

    private StopMapper mStopMapper;
    private List<StopVO> mStops;

    @Inject
    public FavoriteBusStopsPresenter(DataManager dataManager,
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
