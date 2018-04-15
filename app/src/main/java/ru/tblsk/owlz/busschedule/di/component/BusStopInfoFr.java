package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoFragment;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsDialog;

@Subcomponent(modules = FragmentModule.class)
public interface BusStopInfoFr {
    void inject(StopInfoFragment stopInfoFragment);
    void inject(FavoritesDirectionsDialog favoritesDirectionsDialog);
}
