package ru.tblsk.owlz.busschedule.di.screens.busroutes.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.BusRoutesScreen;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.SuburbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.UrbanBusRoutes;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.BusRoutesContainerContract;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.BusRoutesContainerPresenter;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes.BusRoutesContract;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes.SuburbanBusRoutesPresenter;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes.UrbanBusRoutesPresenter;

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
    BusRoutesContract.Presenter provideUrbanRoutesPresenter(
            UrbanBusRoutesPresenter presenter) {
        return presenter;
    }

    @Provides
    @SuburbanBusRoutes
    @BusRoutesScreen
    BusRoutesContract.Presenter provideSuburbanRoutesPresenter(
            SuburbanBusRoutesPresenter presenter) {
        return presenter;
    }

}
