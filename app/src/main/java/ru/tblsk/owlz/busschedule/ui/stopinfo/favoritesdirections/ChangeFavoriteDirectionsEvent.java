package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;



public class ChangeFavoriteDirectionsEvent {

    private boolean isFavorite;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
