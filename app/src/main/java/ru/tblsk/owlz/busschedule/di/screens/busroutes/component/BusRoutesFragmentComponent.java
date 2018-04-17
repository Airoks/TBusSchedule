package ru.tblsk.owlz.busschedule.di.screens.busroutes.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.BusRoutesContainerFragment;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroute.BusRoutesFragment;

@Subcomponent(modules = FragmentModule.class)
public interface BusRoutesFragmentComponent {
    void inject(BusRoutesContainerFragment busRoutesContainerFragment);
    void inject(BusRoutesFragment busRoutesFragment);
}
