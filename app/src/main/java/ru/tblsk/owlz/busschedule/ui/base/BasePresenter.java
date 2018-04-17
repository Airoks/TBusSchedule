package ru.tblsk.owlz.busschedule.ui.base;


import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V mMvpView;

    private final DataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;
    private final SchedulerProvider mSchedulerProvider;


    public BasePresenter(DataManager dataManager,
                         CompositeDisposable compositeDisposable,
                         SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mCompositeDisposable = compositeDisposable;
        this.mSchedulerProvider = schedulerProvider;
    }

    public DataManager getDataManager() {
        return this.mDataManager;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return this.mCompositeDisposable;
    }

    protected SchedulerProvider getSchedulerProvider() {
        return this.mSchedulerProvider;
    }

    @Override
    public void attachView(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mMvpView = null;
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    public boolean isViewAttached() {
        return this.mMvpView != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    public V getMvpView() {
        return this.mMvpView;
    }
}
