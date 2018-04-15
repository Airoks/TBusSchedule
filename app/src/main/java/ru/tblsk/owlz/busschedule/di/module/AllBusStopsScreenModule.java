package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.AllBusStopsScreen;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsContract;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsPresenter;

@Module
public class AllBusStopsScreenModule {

    @Provides
    @AllBusStopsScreen
    AllStopsContract.Presenter provideAllStopsPresenter(
            AllStopsPresenter presenter) {
        return presenter;
    }

}
