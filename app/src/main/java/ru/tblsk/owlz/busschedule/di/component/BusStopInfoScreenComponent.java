package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.BusStopInfoScreen;
import ru.tblsk.owlz.busschedule.di.module.BusStopInfoScreenModule;

@BusStopInfoScreen
@Component(dependencies = ApplicationComponent.class, modules = BusStopInfoScreenModule.class)
public interface BusStopInfoScreenComponent {
}
