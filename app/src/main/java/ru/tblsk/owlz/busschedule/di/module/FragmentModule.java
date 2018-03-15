package ru.tblsk.owlz.busschedule.di.module;


import android.support.v4.app.Fragment;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesPagerAdapter;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;

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
}
