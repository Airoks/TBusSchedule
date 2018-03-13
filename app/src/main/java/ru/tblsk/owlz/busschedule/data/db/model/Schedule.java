package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Property;

@Entity(active = true)
public class Schedule {
    @Expose
    @SerializedName("id")
    @Property(nameInDb = "schedule_id")
    @Id
    private Long id;

    @Expose
    @SerializedName("stops_on_routs_fk")
    @Property(nameInDb = "stops_on_routs_fk")
    @NotNull
    private Long stopsOnRoutsId;

    @Expose
    @SerializedName("schedule_type_fk")
    @Property(nameInDb = "schedule_type_fk")
    @NotNull
    private Long scheduleTypeId;

    @Expose
    @SerializedName("schedule_json")
    @Property(nameInDb = "schedule_json")
    @NotNull
    private String scheduleJson;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1493574644)
    private transient ScheduleDao myDao;

    @Generated(hash = 2011117973)
    public Schedule(Long id, @NotNull Long stopsOnRoutsId,
            @NotNull Long scheduleTypeId, @NotNull String scheduleJson) {
        this.id = id;
        this.stopsOnRoutsId = stopsOnRoutsId;
        this.scheduleTypeId = scheduleTypeId;
        this.scheduleJson = scheduleJson;
    }

    @Generated(hash = 729319394)
    public Schedule() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStopsOnRoutsId() {
        return this.stopsOnRoutsId;
    }

    public void setStopsOnRoutsId(Long stopsOnRoutsId) {
        this.stopsOnRoutsId = stopsOnRoutsId;
    }

    public Long getScheduleTypeId() {
        return this.scheduleTypeId;
    }

    public void setScheduleTypeId(Long scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }

    public String getScheduleJson() {
        return this.scheduleJson;
    }

    public void setScheduleJson(String scheduleJson) {
        this.scheduleJson = scheduleJson;
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
    @Generated(hash = 502317300)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getScheduleDao() : null;
    }

}
