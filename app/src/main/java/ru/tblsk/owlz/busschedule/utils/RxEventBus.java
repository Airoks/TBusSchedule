package ru.tblsk.owlz.busschedule.utils;


import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;

@Singleton
public class RxEventBus {

    private final PublishSubject<Object> mSubject;
    private final BackpressureStrategy mStrategy = BackpressureStrategy.BUFFER;

    public RxEventBus() {
        mSubject = PublishSubject.create();
    }

    public void post(Object event) {
        mSubject.onNext(event);
    }

    public Flowable<Object> observable() {
        return mSubject.toFlowable(mStrategy);
    }

    public <T> Flowable<T> filteredObservable(final Class<T> tClass) {
        return mSubject.ofType(tClass).toFlowable(mStrategy);
    }
}
