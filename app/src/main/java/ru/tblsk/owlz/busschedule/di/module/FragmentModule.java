package ru.tblsk.owlz.busschedule.di.module;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.ActivityContext;
import ru.tblsk.owlz.busschedule.di.annotation.AllBusStops;
import ru.tblsk.owlz.busschedule.di.annotation.SuburbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.annotation.UrbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.annotation.ViewedBusStops;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoAdapter;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoContract;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsAdapter;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsContract;
import ru.tblsk.owlz.busschedule.ui.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.DirectionMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.routes.AllScreenPagerAdapter;
import ru.tblsk.owlz.busschedule.ui.routes.route.RoutesAdapter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleAdapter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoAdapter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoContract;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsAdapter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsContract;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsContract;
import ru.tblsk.owlz.busschedule.ui.stops.viewedstops.StopsContract;
import ru.tblsk.owlz.busschedule.utils.AppConstants;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

@Module
public class FragmentModule {

    private Fragment mFragment;
    private AppCompatActivity mActivity;

    public FragmentModule(AppCompatActivity activity, Fragment fragment) {
        this.mFragment = fragment;
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return this.mActivity;
    }

    @Provides
    @ViewedBusStops
    StopsAdapter provideStopsAdapter(StopsContract.Presenter presenter) {
        return new StopsAdapter(AppConstants.VIEWED_STOPS_ADAPTER, presenter);
    }

    @Provides
    @AllBusStops
    StopsAdapter provideAllStopsAdapter(AllStopsContract.Presenter presenter) {
        return new StopsAdapter(AppConstants.ALL_STOPS_ADAPTER, presenter);
    }

    @Provides
    AllScreenPagerAdapter provideRoutesPagerAdapter() {
        return new AllScreenPagerAdapter(mFragment.getChildFragmentManager());
    }

    @Provides
    DirectionInfoAdapter provideDirectionInfoAdapter(DirectionInfoContract.Presenter presenter) {
        return new DirectionInfoAdapter(presenter);
    }

    @Provides
    @UrbanBusRoutes
    RoutesAdapter provideUrbanRoutesAdapter(RxEventBus eventBus) {
        return new RoutesAdapter(eventBus, AppConstants.URBAN);
    }

    @Provides
    @SuburbanBusRoutes
    RoutesAdapter provideSuburbanRoutesAdapter(RxEventBus eventBus) {
        return new RoutesAdapter(eventBus, AppConstants.SUBURBAN);
    }

    @Provides
    StopInfoAdapter provideStopInfoAdapter(StopInfoContract.Presenter presenter) {
        return new StopInfoAdapter(presenter);
    }

    @Provides
    FavoritesDirectionsAdapter provideFavoritesDirectionsAdapter(
            FavoritesDirectionsContract.Presenter presenter) {
        return new FavoritesDirectionsAdapter(presenter);
    }

    @Provides
    ScheduleAdapter provideScheduleAdapter() {
        return new ScheduleAdapter();
    }

    @Provides
    FavoriteStopsAdapter provideFavoriteStopsAdapter(FavoriteStopsContract.Presenter presenter) {
        return new FavoriteStopsAdapter(presenter);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(@ActivityContext Context context) {
        return new LinearLayoutManager(context);
    }
}
