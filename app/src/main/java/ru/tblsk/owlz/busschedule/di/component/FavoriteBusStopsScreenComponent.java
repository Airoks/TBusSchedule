package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.FavoriteBusStopsScreen;
import ru.tblsk.owlz.busschedule.di.module.FavoriteBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;

@FavoriteBusStopsScreen
@Component(dependencies = ApplicationComponent.class, modules = FavoriteBusStopsScreenModule.class)
public interface FavoriteBusStopsScreenComponent {
    FavoriteBusStopsFr add(FragmentModule fragmentModule);
}
