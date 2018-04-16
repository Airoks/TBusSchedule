package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsFragment;

@Subcomponent(modules = FragmentModule.class)
public interface FavoriteBusStopsFragmentComponent {
    void inject(FavoriteStopsFragment favoriteStopsFragment);
}
