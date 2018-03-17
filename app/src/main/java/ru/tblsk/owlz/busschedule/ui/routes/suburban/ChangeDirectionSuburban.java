package ru.tblsk.owlz.busschedule.ui.routes.suburban;



public class ChangeDirectionSuburban {
    private int position;
    private String direction;

    public ChangeDirectionSuburban(int position, String direction) {
        this.position = position;
        this.direction = direction;
    }

    public int getPosition() {
        return position;
    }

    public String getDirection() {
        return direction;
    }
}
