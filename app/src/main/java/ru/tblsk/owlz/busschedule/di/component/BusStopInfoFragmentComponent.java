package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoFragment;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections.FavoritesDirectionsDialog;

@Subcomponent(modules = FragmentModule.class)
public interface BusStopInfoFragmentComponent {
    void inject(BusStopInfoFragment busStopInfoFragment);
    void inject(FavoritesDirectionsDialog favoritesDirectionsDialog);
}
