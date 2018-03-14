package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.annotation.PerActivity;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerFragment;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsFragment;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsFragment;
import ru.tblsk.owlz.busschedule.ui.splash.SplashActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    FragmentComponent fragmentComponent(FragmentModule fragmentModule);

    void inject(SplashActivity splashActivity);
}
