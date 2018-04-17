package ru.tblsk.owlz.busschedule.di.application.component;


import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.application.module.ApplicationModule;
import ru.tblsk.owlz.busschedule.ui.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.DirectionMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.splashscreen.SplashActivity;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    //что мы хотим получить из AppModule ?
    DataManager getDataManager();
    SchedulerProvider getSchedulerProvider();
    CompositeDisposable getCompositeDisposable();
    RxEventBus getRxEvenBus();
    FlightMapper getFlightMapper();
    StopMapper getStopMapper();
    DirectionMapper getDirectionMapper();
    DepartureTimeMapper getDepartureTimeMapper();

    void inject(SplashActivity activity);
}
