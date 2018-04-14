package ru.tblsk.owlz.busschedule.di.module;


import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.AllStops;
import ru.tblsk.owlz.busschedule.di.annotation.SuburbanRoutes;
import ru.tblsk.owlz.busschedule.di.annotation.UrbanRoutes;
import ru.tblsk.owlz.busschedule.di.annotation.ViewedStops;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoAdapter;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsAdapter;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsContract;
import ru.tblsk.owlz.busschedule.ui.routes.AllScreenPagerAdapter;
import ru.tblsk.owlz.busschedule.ui.routes.route.RoutesAdapter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleAdapter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoAdapter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsAdapter;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;
import ru.tblsk.owlz.busschedule.utils.AppConstants;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @ViewedStops
    StopsAdapter provideStopsAdapter(RxEventBus eventBus) {
        return new StopsAdapter(eventBus, AppConstants.STOPS_ADAPTER);
    }

    @Provides
    @AllStops
    StopsAdapter provideAllStopsAdapter(RxEventBus eventBus) {
        return new StopsAdapter(eventBus, AppConstants.ALL_STOPS_ADAPTER);
    }

    @Provides
    AllScreenPagerAdapter provideRoutesPagerAdapter() {
        return new AllScreenPagerAdapter(mFragment.getChildFragmentManager());
    }

    @Provides
    DirectionInfoAdapter provideDirectionInfoAdapter(RxEventBus eventBus) {
        return new DirectionInfoAdapter(eventBus);
    }

    @Provides
    @UrbanRoutes
    RoutesAdapter provideUrbanRoutesAdapter(RxEventBus eventBus) {
        return new RoutesAdapter(eventBus, AppConstants.URBAN);
    }

    @Provides
    @SuburbanRoutes
    RoutesAdapter provideSuburbanRoutesAdapter(RxEventBus eventBus) {
        return new RoutesAdapter(eventBus, AppConstants.SUBURBAN);
    }

    @Provides
    StopInfoAdapter provideStopInfoAdapter(RxEventBus eventBus) {
        return new StopInfoAdapter(eventBus);
    }

    @Provides
    FavoritesDirectionsAdapter provideFavoritesDirectionsAdapter(RxEventBus eventBus) {
        return new FavoritesDirectionsAdapter(eventBus);
    }

    @Provides
    ScheduleAdapter provideScheduleAdapter() {
        return new ScheduleAdapter();
    }

    @Provides
    FavoriteStopsAdapter provideFavoriteStopsAdapter(FavoriteStopsContract.Presenter presenter) {
        return new FavoriteStopsAdapter(presenter);
    }
}
