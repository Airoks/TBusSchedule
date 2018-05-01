package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.ArrayList;

@Entity(active = true)
public class DepartureTime {
    @Id(autoincrement = true)
    @Property(nameInDb = "departure_time_id")
    private Long id;

    @Expose
    @SerializedName("schedule_fk")
    @Property(nameInDb = "schedule_fk")
    @NotNull
    private Long scheduleId;

    @Expose
    @SerializedName("hours")
    @Property(nameInDb = "hours")
    @NotNull
    @Convert(converter = ListConverter.class, columnType = String.class)
    private ArrayList<Integer> hours;

    @Expose
    @SerializedName("minute")
    @Property(nameInDb = "minute")
    @NotNull
    @Convert(converter = ListConverter.class, columnType = String.class)
    private ArrayList<Integer> minutes;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1428755794)
    private transient DepartureTimeDao myDao;

    @Generated(hash = 734408906)
    public DepartureTime(Long id, @NotNull Long scheduleId,
            @NotNull ArrayList<Integer> hours,
            @NotNull ArrayList<Integer> minutes) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Generated(hash = 2079640216)
    public DepartureTime() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleId() {
        return this.scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public ArrayList<Integer> getHours() {
        return this.hours;
    }

    public void setHours(ArrayList<Integer> hours) {
        this.hours = hours;
    }

    public ArrayList<Integer> getMinutes() {
        return this.minutes;
    }

    public void setMinutes(ArrayList<Integer> minutes) {
        this.minutes = minutes;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1945106138)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDepartureTimeDao() : null;
    }
}
