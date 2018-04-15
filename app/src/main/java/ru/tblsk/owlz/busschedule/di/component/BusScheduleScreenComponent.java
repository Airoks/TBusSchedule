package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.BusScheduleScreen;
import ru.tblsk.owlz.busschedule.di.module.BusScheduleScreenModule;

@BusScheduleScreen
@Component(dependencies = ApplicationComponent.class, modules = BusScheduleScreenModule.class)
public interface BusScheduleScreenComponent {
}
