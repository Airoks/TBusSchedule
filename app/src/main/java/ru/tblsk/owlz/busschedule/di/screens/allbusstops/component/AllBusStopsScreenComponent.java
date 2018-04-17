package ru.tblsk.owlz.busschedule.di.screens.allbusstops.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.screens.allbusstops.AllBusStopsScreen;
import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.screens.allbusstops.module.AllBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;

@AllBusStopsScreen
@Component(dependencies = ApplicationComponent.class, modules = AllBusStopsScreenModule.class)
public interface AllBusStopsScreenComponent {
    AllBusStopsFragmentComponent add(FragmentModule module);
}
