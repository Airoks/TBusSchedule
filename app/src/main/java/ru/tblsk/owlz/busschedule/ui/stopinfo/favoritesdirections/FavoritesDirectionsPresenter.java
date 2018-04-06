package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class FavoritesDirectionsPresenter<V extends FavoritesDirectionsMvpView>
        extends BasePresenter<V> implements FavoritesDirectionsMvpPresenter<V>{

    public FavoritesDirectionsPresenter(DataManager dataManager,
                                        CompositeDisposable compositeDisposable,
                                        SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }
}
