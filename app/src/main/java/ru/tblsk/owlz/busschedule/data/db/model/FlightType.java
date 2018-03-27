package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.SerializedName;

public enum FlightType {
    @SerializedName("0")
    URBAN(0),
    @SerializedName("1")
    SUBURBAN(1);

    final int id;

    FlightType(int id) {
        this.id = id;
    }
}
