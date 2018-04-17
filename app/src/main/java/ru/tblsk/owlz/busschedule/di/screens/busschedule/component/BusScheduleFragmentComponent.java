package ru.tblsk.owlz.busschedule.di.screens.busschedule.component;

import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.BusScheduleContainerFragment;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule.BusScheduleFragment;

@Subcomponent(modules = FragmentModule.class)
public interface BusScheduleFragmentComponent {
    void inject(BusScheduleFragment busScheduleFragment);
    void inject(BusScheduleContainerFragment busScheduleContainerFragment);
}
