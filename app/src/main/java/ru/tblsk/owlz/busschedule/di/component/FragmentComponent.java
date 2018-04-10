package ru.tblsk.owlz.busschedule.di.component;


import dagger.Subcomponent;
import ru.tblsk.owlz.busschedule.di.annotation.PerFragment;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoFragment;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerFragment;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.SuburbanRoutesFragment;
import ru.tblsk.owlz.busschedule.ui.routes.urban.UrbanRoutesFragment;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleFragment;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoFragment;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsDialog;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsFragment;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsFragment;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(StopsFragment stopsFragment);
    void inject(AllStopsFragment allStopsFragment);
    void inject(RoutesContainerFragment routesContainerFragment);
    void inject(UrbanRoutesFragment urbanRoutesFragment);
    void inject(SuburbanRoutesFragment suburbanRoutesFragment);
    void inject(DirectionInfoFragment directionInfoFragment);
    void inject(StopInfoFragment stopInfoFragment);
    void inject(FavoritesDirectionsDialog favoritesDirectionsDialog);
    void inject(ScheduleFragment scheduleFragment);
}
