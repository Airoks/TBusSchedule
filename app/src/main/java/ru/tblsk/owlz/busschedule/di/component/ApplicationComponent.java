package ru.tblsk.owlz.busschedule.di.component;


import javax.inject.Singleton;

import dagger.Component;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;
import ru.tblsk.owlz.busschedule.di.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}
