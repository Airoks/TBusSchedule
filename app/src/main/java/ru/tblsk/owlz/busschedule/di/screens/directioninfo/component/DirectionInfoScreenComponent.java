package ru.tblsk.owlz.busschedule.di.screens.directioninfo.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.screens.directioninfo.DirectionInfoScreen;
import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.screens.directioninfo.module.DirectionInfoScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;

@DirectionInfoScreen
@Component(dependencies = ApplicationComponent.class, modules = DirectionInfoScreenModule.class)
public interface DirectionInfoScreenComponent {
    DirectionInfoFragmentComponent add(FragmentModule module);
}
