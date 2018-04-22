package ru.tblsk.owlz.busschedule.di.application.component;


import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.application.module.ApplicationModule;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.splashscreen.SplashActivity;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    DataManager getDataManager();
    SchedulerProvider getSchedulerProvider();
    CompositeDisposable getCompositeDisposable();
    RxEventBus getRxEvenBus();
    FlightMapper getFlightMapper();
    StopMapper getStopMapper();
    DepartureTimeMapper getDepartureTimeMapper();

    void inject(SplashActivity activity);
    void inject(MainActivity activity);
}
