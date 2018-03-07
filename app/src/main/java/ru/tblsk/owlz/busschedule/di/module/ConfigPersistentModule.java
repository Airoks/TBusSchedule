package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.di.annotation.ConfigPersistent;
import ru.tblsk.owlz.busschedule.ui.main.stops.StopsMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.main.stops.StopsMvpView;
import ru.tblsk.owlz.busschedule.ui.main.stops.StopsPresenter;
import ru.tblsk.owlz.busschedule.ui.splash.SplashMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.splash.SplashMvpView;
import ru.tblsk.owlz.busschedule.ui.splash.SplashPresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.AppSchedulerProvider;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@Module
public class ConfigPersistentModule {

    @Provides
    @ConfigPersistent
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @ConfigPersistent
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> splashPresenter) {
        return splashPresenter;
    }

    @Provides
    @ConfigPersistent
    StopsMvpPresenter<StopsMvpView> provideStopsPresenter(
            StopsPresenter<StopsMvpView> stopsPresenter) {
        return stopsPresenter;
    }

    @Provides
    SchedulerProvider provideScheduler() {
        return new AppSchedulerProvider();
    }
}
