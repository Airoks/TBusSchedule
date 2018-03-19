package ru.tblsk.owlz.busschedule;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.di.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.component.DaggerApplicationComponent;
import ru.tblsk.owlz.busschedule.di.module.ApplicationModule;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;


public class App extends Application {

    @Inject
    RxEventBus mEventBus;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Inject
    SchedulerProvider mSchedulerProvider;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);

        Stetho.initializeWithDefaults(this);
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return  this.mApplicationComponent;
    }

    public RxEventBus getEventBus() {
        return this.mEventBus;
    }

    public CompositeDisposable getCompositeDisposable() {
        return this.mCompositeDisposable;
    }

    public SchedulerProvider getSchedulerProvider() {
        return this.mSchedulerProvider;
    }

}
