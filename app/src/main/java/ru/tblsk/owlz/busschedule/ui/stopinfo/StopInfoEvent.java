package ru.tblsk.owlz.busschedule.ui.stopinfo;



public class StopInfoEvent {
    private long directionId;
    private int directionType;

    public StopInfoEvent() {
    }

    public long getDirectionId() {
        return directionId;
    }

    public void setDirectionId(long directionId) {
        this.directionId = directionId;
    }

    public int getDirectionType() {
        return directionType;
    }

    public void setDirectionType(int directionType) {
        this.directionType = directionType;
    }
}
