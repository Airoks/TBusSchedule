package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.ConfigPersistent;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}
