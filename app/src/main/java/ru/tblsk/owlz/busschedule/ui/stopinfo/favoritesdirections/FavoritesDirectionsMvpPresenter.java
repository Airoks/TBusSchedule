package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;

public interface FavoritesDirectionsMvpPresenter<V extends FavoritesDirectionsMvpView>
        extends MvpPresenter<V>{
    void getFavoritesDirections();
    void subscribeOnEvents();
}
