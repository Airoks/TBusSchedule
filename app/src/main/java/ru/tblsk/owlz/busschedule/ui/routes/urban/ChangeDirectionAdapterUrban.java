package ru.tblsk.owlz.busschedule.ui.routes.urban;



public class ChangeDirectionAdapterUrban {

    private int position;
    private String direction;

    public ChangeDirectionAdapterUrban(int position, String direction) {
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
