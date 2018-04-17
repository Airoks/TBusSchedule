package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.BusRoutesScreen;
import ru.tblsk.owlz.busschedule.di.annotation.SuburbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.annotation.UrbanBusRoutes;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.BusRoutesContainerContract;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.BusRoutesContainerPresenter;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroute.BusRouteContract;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroute.SuburbanBusRoutesPresenter;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroute.UrbanBusRoutesPresenter;

@Module
public class BusRoutesScreenModule {

    @Provides
    @BusRoutesScreen
    BusRoutesContainerContract.Presenter provideRoutesContainerPresenter(
            BusRoutesContainerPresenter presenter) {
        return presenter;
    }

    @Provides
    @UrbanBusRoutes
    @BusRoutesScreen
    BusRouteContract.Presenter provideUrbanRoutesPresenter(
            UrbanBusRoutesPresenter presenter) {
        return presenter;
    }

    @Provides
    @SuburbanBusRoutes
    @BusRoutesScreen
    BusRouteContract.Presenter provideSuburbanRoutesPresenter(
            SuburbanBusRoutesPresenter presenter) {
        return presenter;
    }

}
