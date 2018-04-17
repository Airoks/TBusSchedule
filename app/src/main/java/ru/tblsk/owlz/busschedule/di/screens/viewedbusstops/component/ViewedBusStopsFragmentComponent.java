package ru.tblsk.owlz.busschedule.di.screens.viewedbusstops.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.viewedbusstopsscreen.ViewedBusStopsFragment;

@Subcomponent(modules = FragmentModule.class)
public interface ViewedBusStopsFragmentComponent {
    void inject(ViewedBusStopsFragment viewedBusStopsFragment);
}
