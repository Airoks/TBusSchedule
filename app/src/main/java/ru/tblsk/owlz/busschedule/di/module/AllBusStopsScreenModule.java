package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.AllBusStopsScreen;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen.AllBusStopsContract;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen.AllBusStopsPresenter;

@Module
public class AllBusStopsScreenModule {

    @Provides
    @AllBusStopsScreen
    AllBusStopsContract.Presenter provideAllStopsPresenter(
            AllBusStopsPresenter presenter) {
        return presenter;
    }

}
