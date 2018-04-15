package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.DirectionInfoScreen;
import ru.tblsk.owlz.busschedule.di.module.DirectionInfoScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;

@DirectionInfoScreen
@Component(dependencies = ApplicationComponent.class, modules = DirectionInfoScreenModule.class)
public interface DirectionInfoScreenComponent {
    DirectionInfoFr add(FragmentModule module);
}
