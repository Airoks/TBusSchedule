package ru.tblsk.owlz.busschedule.ui.stopinfo;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.DirectionMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class StopInfoPresenter<V extends StopInfoMvpView> extends BasePresenter<V>
        implements StopInfoMvpPresenter<V>{

    private DirectionMapper mDirectionMapper;
    private Disposable mDisposable;

    @Inject
    public StopInfoPresenter(DataManager dataManager,
                             CompositeDisposable compositeDisposable,
                             SchedulerProvider schedulerProvider,
                             DirectionMapper directionMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mDirectionMapper = directionMapper;
    }

    @Override
    public void getDirectionsByStop(Long stopId) {
        getCompositeDisposable().add(getDataManager().getDirectionsByStop(stopId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .zipWith(getDataManager().getDirectionsByStop(stopId).map(
                        new Function<List<Direction>, List<String>>() {
                    @Override
                    public List<String> apply(List<Direction> directions) throws Exception {
                        return getDataManager().getFlightNumbers(directions);
                    }
                }), mDirectionMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<DirectionVO>>() {
                    @Override
                    public void accept(List<DirectionVO> directionVOS) throws Exception {
                        getMvpView().showDirectionsByStop(directionVOS);
                    }
                }));

    }

    @Override
    public void getSavedDirectionsByStop() {
        getMvpView().showSavedDirectionsByStop();
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }

    @Override
    public void clickedOnButtonAddFavorites() {
        getMvpView().openFavoritesDirectionsDialog();
    }

    @Override
    public void isFavoriteStop(Long stopId) {
        getCompositeDisposable().add(getDataManager().isFavoriteStop(stopId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isFavorite) throws Exception {
                        getMvpView().setFavoriteIcon(isFavorite);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void deleteFavoriteStop(Long stopId) {
        getCompositeDisposable().add(getDataManager().deleteFavoriteStop(stopId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().showSnackBarDeleted();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void setClickListenerForAdapter() {
        if(mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void detachView() {
        if(mDisposable != null) {
            mDisposable.dispose();
        }
        super.detachView();
    }
}
