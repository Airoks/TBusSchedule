package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.SerializedName;

public enum ScheduleType {
    @SerializedName("0")
    WORKDAY(0),
    @SerializedName("1")
    WEEKEND(1);

    int id;

    ScheduleType(int id) {
        this.id = id;
    }
}
