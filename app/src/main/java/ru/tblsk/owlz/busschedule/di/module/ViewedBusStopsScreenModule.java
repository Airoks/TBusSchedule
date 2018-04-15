package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.ViewedBusStopsScreen;
import ru.tblsk.owlz.busschedule.ui.stops.viewedstops.StopsContract;
import ru.tblsk.owlz.busschedule.ui.stops.viewedstops.StopsPresenter;

@Module
public class ViewedBusStopsScreenModule {

    @Provides
    @ViewedBusStopsScreen
    StopsContract.Presenter provideStopsPresenter(
            StopsPresenter presenter) {
        return presenter;
    }

}
