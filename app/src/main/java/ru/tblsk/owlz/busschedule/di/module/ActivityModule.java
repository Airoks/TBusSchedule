package ru.tblsk.owlz.busschedule.di.module;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.ActivityContext;
import ru.tblsk.owlz.busschedule.di.annotation.PerActivity;
import ru.tblsk.owlz.busschedule.di.annotation.WeekendSchedule;
import ru.tblsk.owlz.busschedule.di.annotation.WorkdaySchedule;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoContract;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoPresenter;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsContract;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsPresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.DirectionMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerMvpView;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerPresenter;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.SuburbanRoutesMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.SuburbanRoutesMvpView;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.SuburbanRoutesPresenter;
import ru.tblsk.owlz.busschedule.ui.routes.urban.UrbanRoutesMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.routes.urban.UrbanRoutesMvpView;
import ru.tblsk.owlz.busschedule.ui.routes.urban.UrbanRoutesPresenter;
import ru.tblsk.owlz.busschedule.ui.schedules.ScheduleContainerMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.schedules.ScheduleContainerMvpView;
import ru.tblsk.owlz.busschedule.ui.schedules.ScheduleContainerPresenter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleMvpView;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.WeekendSchedulePresenter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.WorkdaySchedulePresenter;
import ru.tblsk.owlz.busschedule.ui.splash.SplashMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.splash.SplashMvpView;
import ru.tblsk.owlz.busschedule.ui.splash.SplashPresenter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoMvpView;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoPresenter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsMvpView;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsPresenter;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsMvpView;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsPresenter;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsMvpPresenter;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsMvpView;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsPresenter;

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return this.mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return this.mActivity;
    }

    @Provides
    @PerActivity
    RoutesContainerMvpPresenter<RoutesContainerMvpView> provideRoutesContainerPresenter(
            RoutesContainerPresenter<RoutesContainerMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    StopsMvpPresenter<StopsMvpView> provideStopsPresenter(
            StopsPresenter<StopsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AllStopsMvpPresenter<AllStopsMvpView> provideAllStopsPresenter(
            AllStopsPresenter<AllStopsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    UrbanRoutesMvpPresenter<UrbanRoutesMvpView> provideUrbanRoutesPresenter(
            UrbanRoutesPresenter<UrbanRoutesMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SuburbanRoutesMvpPresenter<SuburbanRoutesMvpView> provideSuburbanRoutesPresenter(
            SuburbanRoutesPresenter<SuburbanRoutesMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DirectionInfoContract.Presenter provideDirectionInfoPresenter(
            DirectionInfoPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    StopInfoMvpPresenter<StopInfoMvpView> provideStopInfoPresenter(
            StopInfoPresenter<StopInfoMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FavoritesDirectionsMvpPresenter<FavoritesDirectionsMvpView> provideFavoritesDirectionsPresenter(
            FavoritesDirectionsPresenter<FavoritesDirectionsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @WorkdaySchedule
    @PerActivity
    ScheduleMvpPresenter<ScheduleMvpView> provideWorkdaySchedulePresenter(
            WorkdaySchedulePresenter<ScheduleMvpView> presenter) {
        return presenter;
    }

    @Provides
    @WeekendSchedule
    @PerActivity
    ScheduleMvpPresenter<ScheduleMvpView> provideWeekendSchedulePresenter(
            WeekendSchedulePresenter<ScheduleMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ScheduleContainerMvpPresenter<ScheduleContainerMvpView> provideScheduleContainerPresenter(
            ScheduleContainerPresenter<ScheduleContainerMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FavoriteStopsContract.Presenter provideFavoriteStopsPresenter(
            FavoriteStopsPresenter presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(@ActivityContext Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    FlightMapper provideFlightMapper() {
        return new FlightMapper();
    }

    @Provides
    StopMapper provideStopMapper() {
        return new StopMapper();
    }

    @Provides
    DirectionMapper provideDirectionMapper() {
        return new DirectionMapper();
    }

    @Provides
    DepartureTimeMapper provideDepartureTimeMapper() {
        return new DepartureTimeMapper();
    }

}
