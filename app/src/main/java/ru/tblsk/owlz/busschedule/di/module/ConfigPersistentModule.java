package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.di.annotation.ConfigPersistent;
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
public class ConfigPersistentModule {

    @Provides
    @ConfigPersistent
    RoutesContainerMvpPresenter<RoutesContainerMvpView> provideRoutesContainerPresenter(
            RoutesContainerPresenter<RoutesContainerMvpView> presenter) {
        return presenter;
    }

    @Provides
    @ConfigPersistent
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @ConfigPersistent
    StopsMvpPresenter<StopsMvpView> provideStopsPresenter(
            StopsPresenter<StopsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @ConfigPersistent
    AllStopsMvpPresenter<AllStopsMvpView> provideAllStopsPresenter(
            AllStopsPresenter<AllStopsMvpView> presenter) {
        return presenter;
    }

    @Provides
    SchedulerProvider provideScheduler() {
        return new AppSchedulerProvider();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
