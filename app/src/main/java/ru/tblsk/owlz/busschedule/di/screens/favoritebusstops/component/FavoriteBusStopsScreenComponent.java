package ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.FavoriteBusStopsScreen;
import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.module.FavoriteBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;

@FavoriteBusStopsScreen
@Component(dependencies = ApplicationComponent.class, modules = FavoriteBusStopsScreenModule.class)
public interface FavoriteBusStopsScreenComponent {
    FavoriteBusStopsFragmentComponent add(FragmentModule fragmentModule);
}
