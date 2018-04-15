package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.AllBusStopsScreen;
import ru.tblsk.owlz.busschedule.di.module.AllBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;

@AllBusStopsScreen
@Component(dependencies = ApplicationComponent.class, modules = AllBusStopsScreenModule.class)
public interface AllBusStopsScreenComponent {
    AllBusStopsFr add(FragmentModule module);
}
