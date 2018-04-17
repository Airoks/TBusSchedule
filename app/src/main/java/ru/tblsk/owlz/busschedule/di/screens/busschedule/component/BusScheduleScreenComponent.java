package ru.tblsk.owlz.busschedule.di.screens.busschedule.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.BusScheduleScreen;
import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.module.BusScheduleScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;

@BusScheduleScreen
@Component(dependencies = ApplicationComponent.class, modules = BusScheduleScreenModule.class)
public interface BusScheduleScreenComponent {
    BusScheduleFragmentComponent add(FragmentModule module);
}
