package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.BusStopInfoScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.DirectionAdditionEvent;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@BusStopInfoScreen
public class FavoritesDirectionsPresenter extends BasePresenter<FavoritesDirectionsContract.View>
        implements FavoritesDirectionsContract.Presenter {

    private List<DirectionVO> mDirections;
    private List<DirectionVO> mFavoriteDirections;
    private long mStopId;
    private RxEventBus mEventBus;

    @Inject
    public FavoritesDirectionsPresenter(DataManager dataManager,
                                        CompositeDisposable compositeDisposable,
                                        SchedulerProvider schedulerProvider,
                                        RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);

        mEventBus = eventBus;
    }

    @Override
    public void getFavoritesDirections() {
        getMvpView().showDirections(mDirections);
    }

    @Override
    public void setData(List<DirectionVO> directions, long stopId) {
        mStopId = stopId;
        mDirections = getCopyOfDirections(directions);
    }

    @Override
    public void clickedOnAddButton() {
        List<Long> directionsId = getFavoriteDirectionsId();
        if(!directionsId.isEmpty()) {
            addFavoriteDirections(mStopId, directionsId);
            DirectionAdditionEvent event = new DirectionAdditionEvent(true);
            event.setFavorites(mFavoriteDirections);
            mEventBus.post(event);
            getMvpView().closeDialog();
        } else {
            mEventBus.post(new DirectionAdditionEvent(false));
            getMvpView().closeDialog();
        }
    }

    @Override
    public void clickedOnAdapterItem(int position, boolean isFavorite) {
        DirectionVO direction = mDirections.get(position);
        direction.setFavorite(isFavorite);
        mDirections.set(position, direction);
    }

    @Override
    public void addFavoriteDirections(long stopId, List<Long> directions) {
        getCompositeDisposable().add(getDataManager().insertFavoriteStops(stopId, directions)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    private List<Long> getFavoriteDirectionsId() {
        mFavoriteDirections = new ArrayList<>();
        List<Long> directionsId = new ArrayList<>();
        for(DirectionVO direction : mDirections) {
            if(direction.isFavorite()) {
                directionsId.add(direction.getId());
                mFavoriteDirections.add(direction);
            }
        }
        return directionsId;
    }

    private List<DirectionVO> getCopyOfDirections(List<DirectionVO> directions) {
        List<DirectionVO> copy = new ArrayList<>();
        List<DirectionVO> original;
        original = directions;
        for (DirectionVO direction : original) {
            DirectionVO copyDirection = new DirectionVO();

            copyDirection.setId(direction.getId());
            copyDirection.setFlightId(direction.getFlightId());
            copyDirection.setFavorite(direction.isFavorite());
            copyDirection.setFlightNumber(direction.getFlightNumber());
            copyDirection.setDirectionType(direction.getDirectionType());
            copyDirection.setDirectionName(direction.getDirectionName());

            copy.add(copyDirection);
        }
        return copy;
    }
}
