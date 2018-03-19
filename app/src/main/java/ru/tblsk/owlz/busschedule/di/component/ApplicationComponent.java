package ru.tblsk.owlz.busschedule.di.component;


import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;
import ru.tblsk.owlz.busschedule.di.module.ApplicationModule;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(App app);
    //что мы хотим получить из AppModule ?
    DataManager getDataManager();
    SchedulerProvider getSchedulerProvider();
    CompositeDisposable getCompositeDisposable();
    RxEventBus getRxEvenBus();
}
