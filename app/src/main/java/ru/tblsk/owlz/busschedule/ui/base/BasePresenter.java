package ru.tblsk.owlz.busschedule.ui.base;



public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V mMvpView;

    @Override
    public void attachView(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mMvpView = null;
    }

    public boolean isViewAttached() {
        return this.mMvpView != null;
    }

    public V getMvpView() {
        return this.mMvpView;
    }
}
