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
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroute.BusRoutesAdapter;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoAdapter;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.BusStopsAdapter;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen.AllBusStopsContract;
import ru.tblsk.owlz.busschedule.ui.directioninfoscreen.DirectionInfoAdapter;
import ru.tblsk.owlz.busschedule.ui.directioninfoscreen.DirectionInfoContract;
import ru.tblsk.owlz.busschedule.ui.favoritebusstopsscreen.FavoriteBusStopsAdapter;
import ru.tblsk.owlz.busschedule.ui.favoritebusstopsscreen.FavoriteBusStopsContract;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.AllScreenPagerAdapter;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule.BusScheduleAdapter;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoContract;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections.FavoritesDirectionsAdapter;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections.FavoritesDirectionsContract;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.viewedbusstopsscreen.ViewedBusStopsContract;
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
    BusStopsAdapter provideStopsAdapter(ViewedBusStopsContract.Presenter presenter) {
        return new BusStopsAdapter(AppConstants.VIEWED_STOPS_ADAPTER, presenter);
    }

    @Provides
    @AllBusStops
    BusStopsAdapter provideAllStopsAdapter(AllBusStopsContract.Presenter presenter) {
        return new BusStopsAdapter(AppConstants.ALL_STOPS_ADAPTER, presenter);
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
    BusRoutesAdapter provideUrbanRoutesAdapter(RxEventBus eventBus) {
        return new BusRoutesAdapter(eventBus, AppConstants.URBAN);
    }

    @Provides
    @SuburbanBusRoutes
    BusRoutesAdapter provideSuburbanRoutesAdapter(RxEventBus eventBus) {
        return new BusRoutesAdapter(eventBus, AppConstants.SUBURBAN);
    }

    @Provides
    BusStopInfoAdapter provideStopInfoAdapter(BusStopInfoContract.Presenter presenter) {
        return new BusStopInfoAdapter(presenter);
    }

    @Provides
    FavoritesDirectionsAdapter provideFavoritesDirectionsAdapter(
            FavoritesDirectionsContract.Presenter presenter) {
        return new FavoritesDirectionsAdapter(presenter);
    }

    @Provides
    BusScheduleAdapter provideScheduleAdapter() {
        return new BusScheduleAdapter();
    }

    @Provides
    FavoriteBusStopsAdapter provideFavoriteStopsAdapter(FavoriteBusStopsContract.Presenter presenter) {
        return new FavoriteBusStopsAdapter(presenter);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(@ActivityContext Context context) {
        return new LinearLayoutManager(context);
    }
}
