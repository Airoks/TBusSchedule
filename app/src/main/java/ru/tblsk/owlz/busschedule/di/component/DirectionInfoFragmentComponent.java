package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoFragment;

@Subcomponent(modules = FragmentModule.class)
public interface DirectionInfoFragmentComponent {
    void inject(DirectionInfoFragment directionInfoFragment);
}
