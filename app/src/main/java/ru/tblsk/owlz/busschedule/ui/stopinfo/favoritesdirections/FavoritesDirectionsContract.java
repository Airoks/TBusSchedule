package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;

public interface FavoritesDirectionsContract {

    interface View extends MvpView {
        void showDirections(List<DirectionVO> directions);
        void addedFavoriteDirections(boolean isAdded);
    }

    interface Presenter extends MvpPresenter<View> {
        void getFavoritesDirections();
        void setData(List<DirectionVO> directions, long stopId);
        void clickedOnAddButton();
        void clickedOnAdapterItem(int position, boolean isFavorite);
        void addFavoriteDirections(long stopId, List<Long> directions);
    }
}
