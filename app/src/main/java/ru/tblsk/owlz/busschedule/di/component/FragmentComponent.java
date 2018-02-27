package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.annotation.PerFragment;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public class FragmentComponent {
}
