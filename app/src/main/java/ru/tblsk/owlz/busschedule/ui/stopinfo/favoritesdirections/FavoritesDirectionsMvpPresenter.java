package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface FavoritesDirectionsMvpPresenter<V extends FavoritesDirectionsMvpView>
        extends MvpPresenter<V>{
    void getFavoritesDirections();
    void subscribeOnEvents();
    void addFavoriteDirections(long stopId, List<Long> directions);
}
