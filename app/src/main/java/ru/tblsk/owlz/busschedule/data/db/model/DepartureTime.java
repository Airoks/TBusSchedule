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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Integer> hours;

    @Expose
    @SerializedName("time")
    @Property(nameInDb = "time")
    @NotNull
    @Convert(converter = MapConverter.class, columnType = String.class)
    private Map<Integer, ArrayList<Integer>> time;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1428755794)
    private transient DepartureTimeDao myDao;

    @Generated(hash = 39201435)
    public DepartureTime(Long id, @NotNull Long scheduleId,
            @NotNull List<Integer> hours,
            @NotNull Map<Integer, ArrayList<Integer>> time) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.hours = hours;
        this.time = time;
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

    public List<Integer> getHours() {
        return this.hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours = hours;
    }

    public Map<Integer, ArrayList<Integer>> getTime() {
        return this.time;
    }

    public void setTime(Map<Integer, ArrayList<Integer>> time) {
        this.time = time;
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
