package ru.tblsk.owlz.busschedule.di.screens.viewedbusstops.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.screens.viewedbusstops.ViewedBusStopsScreen;
import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.di.screens.viewedbusstops.module.ViewedBusStopsScreenModule;

@ViewedBusStopsScreen
@Component(dependencies = ApplicationComponent.class, modules = ViewedBusStopsScreenModule.class)
public interface ViewedBusStopsScreenComponent {
    ViewedBusStopsFragmentComponent add(FragmentModule module);
}
