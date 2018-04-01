package ru.tblsk.owlz.busschedule.ui.viewobject;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.Direction;

public class FlightVO implements Parcelable{
    private Long id;
    private int flightType;
    private String flightNumber;
    private List<Direction> directions;

    protected FlightVO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        flightType = in.readInt();
        flightNumber = in.readString();
        directions = in.createTypedArrayList(Direction.CREATOR);
    }

    public static final Creator<FlightVO> CREATOR = new Creator<FlightVO>() {
        @Override
        public FlightVO createFromParcel(Parcel in) {
            return new FlightVO(in);
        }

        @Override
        public FlightVO[] newArray(int size) {
            return new FlightVO[size];
        }
    };

    public FlightVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFlightType() {
        return flightType;
    }

    public void setFlightType(int flightType) {
        this.flightType = flightType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeInt(flightType);
        parcel.writeString(flightNumber);
        parcel.writeTypedList(directions);
    }
}
