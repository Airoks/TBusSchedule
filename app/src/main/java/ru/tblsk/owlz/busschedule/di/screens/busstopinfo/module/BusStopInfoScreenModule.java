package ru.tblsk.owlz.busschedule.di.screens.busstopinfo.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.BusStopInfo;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.BusStopInfoScreen;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.FavoriteBusStopInfo;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.FavoriteBusStopInfoPresenter;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoContract;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoPresenter;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections.FavoritesDirectionsContract;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections.FavoritesDirectionsPresenter;

@Module
public class BusStopInfoScreenModule {

    @Provides
    @BusStopInfo
    @BusStopInfoScreen
    BusStopInfoContract.Presenter provideStopInfoPresenter(
            BusStopInfoPresenter presenter) {
        return presenter;
    }

    @Provides
    @FavoriteBusStopInfo
    @BusStopInfoScreen
    BusStopInfoContract.Presenter provideFavoriteStopInfoPresenter(
            FavoriteBusStopInfoPresenter presenter) {
       return presenter;
    }

    @Provides
    @BusStopInfoScreen
    FavoritesDirectionsContract.Presenter provideFavoritesDirectionsPresenter(
            FavoritesDirectionsPresenter presenter) {
        return presenter;
    }

}
