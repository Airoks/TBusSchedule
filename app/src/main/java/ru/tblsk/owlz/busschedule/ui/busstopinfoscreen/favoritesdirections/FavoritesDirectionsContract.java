package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DirectionVO;

public interface FavoritesDirectionsContract {

    interface View extends MvpView {
        void showDirections(List<DirectionVO> directions);
        void closeDialog();
    }

    interface Presenter extends MvpPresenter<View> {
        void getFavoritesDirections();
        void setData(List<DirectionVO> directions, long stopId);
        void clickedOnAddButton();
        void clickedOnAdapterItem(int position, boolean isFavorite);
        void addFavoriteDirections(long stopId, List<Long> directions);
    }
}
