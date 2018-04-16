package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.stops.viewedstops.StopsFragment;

@Subcomponent(modules = FragmentModule.class)
public interface ViewedBusStopsFragmentComponent {
    void inject(StopsFragment stopsFragment);
}
