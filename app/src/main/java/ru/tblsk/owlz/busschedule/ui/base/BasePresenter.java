package ru.tblsk.owlz.busschedule.ui.base;



public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V mvpView;

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    public boolean isViewAttached() {
        return this.mvpView != null;
    }

    public V getMvpView() {
        return this.mvpView;
    }
}
