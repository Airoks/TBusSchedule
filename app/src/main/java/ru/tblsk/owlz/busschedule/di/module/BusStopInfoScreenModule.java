package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.BusStopInfoScreen;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoContract;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoPresenter;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections.FavoritesDirectionsContract;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections.FavoritesDirectionsPresenter;

@Module
public class BusStopInfoScreenModule {

    @Provides
    @BusStopInfoScreen
    BusStopInfoContract.Presenter provideStopInfoPresenter(
            BusStopInfoPresenter presenter) {
        return presenter;
    }

    @Provides
    @BusStopInfoScreen
    FavoritesDirectionsContract.Presenter provideFavoritesDirectionsPresenter(
            FavoritesDirectionsPresenter presenter) {
        return presenter;
    }

}
