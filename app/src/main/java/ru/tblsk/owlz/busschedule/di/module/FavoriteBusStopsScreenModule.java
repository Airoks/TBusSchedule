package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.FavoriteBusStopsScreen;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsContract;
import ru.tblsk.owlz.busschedule.ui.favorites.FavoriteStopsPresenter;

@Module
public class FavoriteBusStopsScreenModule {

    @Provides
    @FavoriteBusStopsScreen
    FavoriteStopsContract.Presenter provideFavoriteStopsPresenter(
            FavoriteStopsPresenter presenter) {
        return presenter;
    }

}
