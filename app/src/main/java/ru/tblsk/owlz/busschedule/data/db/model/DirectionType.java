package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.SerializedName;

public enum DirectionType {
    @SerializedName("0")
    DIRECT(0),
    @SerializedName("1")
    REVERSE(1);

    public final int id;

    DirectionType(int id) {
        this.id = id;
    }
}
