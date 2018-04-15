package ru.tblsk.owlz.busschedule.di.component;

import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.schedules.ScheduleContainerFragment;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleFragment;

@Subcomponent(modules = FragmentModule.class)
public interface BusScheduleFr {
    void inject(ScheduleFragment scheduleFragment);
    void inject(ScheduleContainerFragment scheduleContainerFragment);
}
