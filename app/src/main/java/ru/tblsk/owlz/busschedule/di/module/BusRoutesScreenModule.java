package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.BusRoutesScreen;
import ru.tblsk.owlz.busschedule.di.annotation.SuburbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.annotation.UrbanBusRoutes;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerContract;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerPresenter;
import ru.tblsk.owlz.busschedule.ui.routes.route.RouteContract;
import ru.tblsk.owlz.busschedule.ui.routes.route.SuburbanRoutesPresenter;
import ru.tblsk.owlz.busschedule.ui.routes.route.UrbanRoutesPresenter;

@Module
public class BusRoutesScreenModule {

    @Provides
    @BusRoutesScreen
    RoutesContainerContract.Presenter provideRoutesContainerPresenter(
            RoutesContainerPresenter presenter) {
        return presenter;
    }

    @Provides
    @UrbanBusRoutes
    @BusRoutesScreen
    RouteContract.Presenter provideUrbanRoutesPresenter(
            UrbanRoutesPresenter presenter) {
        return presenter;
    }

    @Provides
    @SuburbanBusRoutes
    @BusRoutesScreen
    RouteContract.Presenter provideSuburbanRoutesPresenter(
            SuburbanRoutesPresenter presenter) {
        return presenter;
    }

}
