package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.BusRoutesScreen;
import ru.tblsk.owlz.busschedule.di.module.BusRoutesScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;

@BusRoutesScreen
@Component(dependencies = ApplicationComponent.class, modules = BusRoutesScreenModule.class)
public interface BusRoutesScreenComponent {
    BusRoutesFragmentComponent add(FragmentModule module);
}
