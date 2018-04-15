package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsFragment;

@Subcomponent(modules = FragmentModule.class)
public interface AllBusStopsFr {
    void inject(AllStopsFragment allStopsFragment);
}
