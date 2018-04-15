package ru.tblsk.owlz.busschedule.di.component;


import dagger.Component;
import ru.tblsk.owlz.busschedule.di.annotation.DirectionInfoScreen;
import ru.tblsk.owlz.busschedule.di.module.DirectionInfoScreenModule;

@DirectionInfoScreen
@Component(dependencies = ApplicationComponent.class, modules = DirectionInfoScreenModule.class)
public interface DirectionInfoScreenComponent {
}
