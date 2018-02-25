package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;
import ru.tblsk.owlz.busschedule.di.module.ApplicationModule;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}
