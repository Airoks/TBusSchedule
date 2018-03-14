package ru.tblsk.owlz.busschedule.di.module;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.di.annotation.ActivityContext;
import ru.tblsk.owlz.busschedule.di.annotation.PerActivity;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerMvpView;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerPresenter;
import ru.tblsk.owlz.busschedule.ui.splash.SplashMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.splash.SplashMvpView;
import ru.tblsk.owlz.busschedule.ui.splash.SplashPresenter;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsMvpView;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsPresenter;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsMvpView;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsPresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.AppSchedulerProvider;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return this.mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return this.mActivity;
    }

    @Provides
    @PerActivity
    RoutesContainerMvpPresenter<RoutesContainerMvpView> provideRoutesContainerPresenter(
            RoutesContainerPresenter<RoutesContainerMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    StopsMvpPresenter<StopsMvpView> provideStopsPresenter(
            StopsPresenter<StopsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AllStopsMvpPresenter<AllStopsMvpView> provideAllStopsPresenter(
            AllStopsPresenter<AllStopsMvpView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideScheduler() {
        return new AppSchedulerProvider();
    }
}
