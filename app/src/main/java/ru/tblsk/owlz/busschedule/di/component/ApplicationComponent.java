package ru.tblsk.owlz.busschedule.di.component;


import javax.inject.Singleton;

import dagger.Component;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;
import ru.tblsk.owlz.busschedule.di.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    //что мы хотим получить из AppModule ?
    DataManager getDataManager();
}
