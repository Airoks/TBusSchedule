package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.ViewedBusStopsScreen;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.di.module.ViewedBusStopsScreenModule;

@ViewedBusStopsScreen
@Component(dependencies = ApplicationComponent.class, modules = ViewedBusStopsScreenModule.class)
public interface ViewedBusStopsScreenComponent {
    ViewedBusStopsFr add(FragmentModule module);
}
