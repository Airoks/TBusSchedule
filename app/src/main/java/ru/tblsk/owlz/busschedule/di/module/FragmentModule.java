package ru.tblsk.owlz.busschedule.di.module;


import android.support.v4.app.Fragment;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.di.annotation.FlightType;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoAdapter;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesAdapter;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesPagerAdapter;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    StopsAdapter provideStopsAdapter() {
        return new StopsAdapter(new ArrayList<Stop>());
    }

    @Provides
    RoutesPagerAdapter provideRoutesPagerAdapter() {
        return new RoutesPagerAdapter(mFragment.getChildFragmentManager());
    }

    @Provides
    DirectionInfoAdapter provideDirectionInfoAdapter(RxEventBus eventBus) {
        return new DirectionInfoAdapter(eventBus);
    }

    @Provides
    @FlightType("urban")
    RoutesAdapter provideUrbanRoutesAdapter(RxEventBus eventBus) {
        return new RoutesAdapter(eventBus, "urban");
    }

    @Provides
    @FlightType("suburban")
    RoutesAdapter provideSuburbanRoutesAdapter(RxEventBus eventBus) {
        return new RoutesAdapter(eventBus, "suburban");
    }
}
