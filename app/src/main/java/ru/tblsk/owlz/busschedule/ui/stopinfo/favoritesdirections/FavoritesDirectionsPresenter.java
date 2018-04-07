package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class FavoritesDirectionsPresenter<V extends FavoritesDirectionsMvpView>
        extends BasePresenter<V> implements FavoritesDirectionsMvpPresenter<V> {

    private RxEventBus mEventBus;

    @Inject
    public FavoritesDirectionsPresenter(DataManager dataManager,
                                        CompositeDisposable compositeDisposable,
                                        SchedulerProvider schedulerProvider,
                                        RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mEventBus = eventBus;
    }

    @Override
    public void getFavoritesDirections() {
        getMvpView().showDirections();
    }

    @Override
    public void subscribeOnEvents() {
        if(getCompositeDisposable().size() == 0) {
            getCompositeDisposable().add(mEventBus.filteredObservable(ChangeFavoriteDirection.class)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<ChangeFavoriteDirection>() {
                        @Override
                        public void accept(ChangeFavoriteDirection change) throws Exception {
                            getMvpView().changeFavoriteDirections(change.getPosition(),
                                    change.isFavorite());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));
        }
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
}
