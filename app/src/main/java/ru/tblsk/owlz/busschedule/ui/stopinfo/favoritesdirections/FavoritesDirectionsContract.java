package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface FavoritesDirectionsContract {

    interface View extends MvpView {
        void showDirections();
        void changeFavoriteDirections(int position, boolean isFavorite);
    }

    interface Presenter extends MvpPresenter<View> {
        void getFavoritesDirections();
        void subscribeOnEvents();
        void addFavoriteDirections(long stopId, List<Long> directions);
    }
}
