package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerFragment;
import ru.tblsk.owlz.busschedule.ui.routes.route.RoutesFragment;

@Subcomponent(modules = FragmentModule.class)
public interface BusRoutesFr {
    void inject(RoutesContainerFragment routesContainerFragment);
    void inject(RoutesFragment routesFragment);
}
