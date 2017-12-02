package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(active = true)
public class JourneyType {
    @Id(autoincrement = true)
    private long JourneyTypeId;

    @NotNull
    private String JourneyTypeName;

    public String getJourneyTypeName() {
        return JourneyTypeName;
    }

    public void setJourneyTypeName(String journeyTypeName) {
        JourneyTypeName = journeyTypeName;
    }

    public long getJourneyTypeId() {

        return JourneyTypeId;
    }

    public void setJourneyTypeId(long journeyTypeId) {
        JourneyTypeId = journeyTypeId;
    }
}
