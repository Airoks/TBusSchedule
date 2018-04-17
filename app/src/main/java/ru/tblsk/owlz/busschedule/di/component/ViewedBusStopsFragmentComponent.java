package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.viewedbusstopsscreen.ViewedBusStopsFragment;

@Subcomponent(modules = FragmentModule.class)
public interface ViewedBusStopsFragmentComponent {
    void inject(ViewedBusStopsFragment viewedBusStopsFragment);
}
