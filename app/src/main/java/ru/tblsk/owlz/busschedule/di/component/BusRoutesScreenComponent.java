package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.BusRoutesScreen;
import ru.tblsk.owlz.busschedule.di.module.BusRoutesScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FavoriteBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.module.ViewedBusStopsScreenModule;

@BusRoutesScreen
@Component(dependencies = ApplicationComponent.class, modules = BusRoutesScreenModule.class)
public interface BusRoutesScreenComponent {
    ViewedBusStopsScreenComponent viewedBusStopsComp(ViewedBusStopsScreenModule module);
    FavoriteBusStopsScreenComponent favoriteBusStopsComp(FavoriteBusStopsScreenModule module);
}
