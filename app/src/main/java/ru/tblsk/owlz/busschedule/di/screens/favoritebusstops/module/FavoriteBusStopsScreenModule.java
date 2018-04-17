package ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.FavoriteBusStopsScreen;
import ru.tblsk.owlz.busschedule.ui.favoritebusstopsscreen.FavoriteBusStopsContract;
import ru.tblsk.owlz.busschedule.ui.favoritebusstopsscreen.FavoriteBusStopsPresenter;

@Module
public class FavoriteBusStopsScreenModule {

    @Provides
    @FavoriteBusStopsScreen
    FavoriteBusStopsContract.Presenter provideFavoriteStopsPresenter(
            FavoriteBusStopsPresenter presenter) {
        return presenter;
    }

}
