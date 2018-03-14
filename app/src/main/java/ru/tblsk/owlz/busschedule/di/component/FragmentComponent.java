package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.annotation.PerFragment;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerFragment;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsFragment;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsFragment;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(StopsFragment stopsFragment);
    void inject(AllStopsFragment allStopsFragment);
    void inject(RoutesContainerFragment routesContainerFragment);
}
