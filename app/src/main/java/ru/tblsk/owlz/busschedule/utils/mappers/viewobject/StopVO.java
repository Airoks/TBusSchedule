package ru.tblsk.owlz.busschedule.utils.mappers.viewobject;


import android.os.Parcel;
import android.os.Parcelable;

public class StopVO implements Parcelable{
    private Long id;
    private String stopName;

    public StopVO() {
    }

    protected StopVO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        stopName = in.readString();
    }

    public static final Creator<StopVO> CREATOR = new Creator<StopVO>() {
        @Override
        public StopVO createFromParcel(Parcel in) {
            return new StopVO(in);
        }

        @Override
        public StopVO[] newArray(int size) {
            return new StopVO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
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
        parcel.writeString(stopName);
    }
}
