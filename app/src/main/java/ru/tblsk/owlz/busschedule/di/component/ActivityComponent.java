package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.annotation.PerActivity;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsFragment;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsFragment;
import ru.tblsk.owlz.busschedule.ui.splash.SplashActivity;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);
    void inject(StopsFragment stopsFragment);
    void inject(AllStopsFragment allStopsFragment);
}
