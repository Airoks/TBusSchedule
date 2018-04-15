package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.BusStopInfoScreen;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoContract;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoPresenter;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsContract;
import ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections.FavoritesDirectionsPresenter;

@Module
public class BusStopInfoScreenModule {

    @Provides
    @BusStopInfoScreen
    StopInfoContract.Presenter provideStopInfoPresenter(
            StopInfoPresenter presenter) {
        return presenter;
    }

    @Provides
    @BusStopInfoScreen
    FavoritesDirectionsContract.Presenter provideFavoritesDirectionsPresenter(
            FavoritesDirectionsPresenter presenter) {
        return presenter;
    }

}
