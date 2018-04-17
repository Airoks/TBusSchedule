package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.ViewedBusStopsScreen;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.viewedbusstopsscreen.ViewedBusStopsContract;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.viewedbusstopsscreen.ViewedBusStopsPresenter;

@Module
public class ViewedBusStopsScreenModule {

    @Provides
    @ViewedBusStopsScreen
    ViewedBusStopsContract.Presenter provideStopsPresenter(
            ViewedBusStopsPresenter presenter) {
        return presenter;
    }

}
