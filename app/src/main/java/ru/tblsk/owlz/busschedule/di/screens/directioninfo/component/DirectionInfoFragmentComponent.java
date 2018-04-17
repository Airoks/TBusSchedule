package ru.tblsk.owlz.busschedule.di.screens.directioninfo.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.directioninfoscreen.DirectionInfoFragment;

@Subcomponent(modules = FragmentModule.class)
public interface DirectionInfoFragmentComponent {
    void inject(DirectionInfoFragment directionInfoFragment);
}
