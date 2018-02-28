package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.annotation.PerActivity;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    //inject
}
