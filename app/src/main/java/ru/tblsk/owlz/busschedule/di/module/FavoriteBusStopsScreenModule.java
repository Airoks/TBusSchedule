package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.FavoriteBusStopsScreen;
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
