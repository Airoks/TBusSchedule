package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface FavoritesDirectionsMvpView extends MvpView {
    void showDirections();
    void changeFavoriteDirections(int position, boolean isFavorite);
}
