package ru.tblsk.owlz.busschedule.di.screens.allbusstops.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen.AllBusStopsFragment;

@Subcomponent(modules = FragmentModule.class)
public interface AllBusStopsFragmentComponent {
    void inject(AllBusStopsFragment allBusStopsFragment);
}
