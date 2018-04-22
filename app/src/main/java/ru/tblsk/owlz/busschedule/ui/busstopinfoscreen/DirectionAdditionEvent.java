package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen;


import java.util.ArrayList;
import java.util.List;

import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DirectionVO;

public class DirectionAdditionEvent {
    private boolean isAdded;
    private List<DirectionVO> favorites;

    public DirectionAdditionEvent(boolean isAdded) {
        this.isAdded = isAdded;
        this.favorites = new ArrayList<>();
    }

    public boolean isAdded() {
        return isAdded;
    }

    public List<DirectionVO> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<DirectionVO> favorites) {
        this.favorites.addAll(favorites);
    }
}
