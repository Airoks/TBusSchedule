package ru.tblsk.owlz.busschedule.di.screens.busstopinfo.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.BusStopInfoScreen;
import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.module.BusStopInfoScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;

@BusStopInfoScreen
@Component(dependencies = ApplicationComponent.class, modules = BusStopInfoScreenModule.class)
public interface BusStopInfoScreenComponent {
    BusStopInfoFragmentComponent add(FragmentModule module);
}
