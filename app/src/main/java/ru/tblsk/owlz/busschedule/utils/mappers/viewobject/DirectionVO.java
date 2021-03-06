package ru.tblsk.owlz.busschedule.utils.mappers.viewobject;


import android.os.Parcel;
import android.os.Parcelable;

public class DirectionVO implements Parcelable{
    private Long id;
    private Long flightId;
    private int directionType;
    private String directionName;
    private String flightNumber;
    private boolean isFavorite;

    public DirectionVO() {
    }

    protected DirectionVO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            flightId = null;
        } else {
            flightId = in.readLong();
        }
        directionType = in.readInt();
        directionName = in.readString();
        flightNumber = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<DirectionVO> CREATOR = new Creator<DirectionVO>() {
        @Override
        public DirectionVO createFromParcel(Parcel in) {
            return new DirectionVO(in);
        }

        @Override
        public DirectionVO[] newArray(int size) {
            return new DirectionVO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public int getDirectionType() {
        return directionType;
    }

    public void setDirectionType(int directionType) {
        this.directionType = directionType;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
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
        if (flightId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(flightId);
        }
        parcel.writeInt(directionType);
        parcel.writeString(directionName);
        parcel.writeString(flightNumber);
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
