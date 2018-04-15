package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.AllBusStopsScreen;
import ru.tblsk.owlz.busschedule.di.module.AllBusStopsScreenModule;

@AllBusStopsScreen
@Component(dependencies = ApplicationComponent.class, modules = AllBusStopsScreenModule.class)
public interface AllBusStopsScreenComponent {
}
