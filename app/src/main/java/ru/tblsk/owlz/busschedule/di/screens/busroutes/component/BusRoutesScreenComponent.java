package ru.tblsk.owlz.busschedule.di.screens.busroutes.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.BusRoutesScreen;
import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.module.BusRoutesScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;

@BusRoutesScreen
@Component(dependencies = ApplicationComponent.class, modules = BusRoutesScreenModule.class)
public interface BusRoutesScreenComponent {
    BusRoutesFragmentComponent add(FragmentModule module);
}
