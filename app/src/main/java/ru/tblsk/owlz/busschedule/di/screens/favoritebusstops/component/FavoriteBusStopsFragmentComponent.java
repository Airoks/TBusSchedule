package ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.favoritebusstopsscreen.FavoriteBusStopsFragment;

@Subcomponent(modules = FragmentModule.class)
public interface FavoriteBusStopsFragmentComponent {
    void inject(FavoriteBusStopsFragment favoriteBusStopsFragment);
}
