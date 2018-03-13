package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true)
public class ScheduleType {
    @Expose
    @SerializedName("id")
    @Property(nameInDb = "schedule_type_id")
    @Id
    private Long id;

    @Expose
    @SerializedName("schedule_type_name")
    @Property(nameInDb = "schedule_type_name")
    @NotNull
    private String scheduleTypeName;

    @ToMany(referencedJoinProperty = "scheduleTypeId")
    private List<Schedule> schedules;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1056866477)
    private transient ScheduleTypeDao myDao;

    @Generated(hash = 1423305045)
    public ScheduleType(Long id, @NotNull String scheduleTypeName) {
        this.id = id;
        this.scheduleTypeName = scheduleTypeName;
    }

    @Generated(hash = 770820158)
    public ScheduleType() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScheduleTypeName() {
        return this.scheduleTypeName;
    }

    public void setScheduleTypeName(String scheduleTypeName) {
        this.scheduleTypeName = scheduleTypeName;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1341738165)
    public List<Schedule> getSchedules() {
        if (schedules == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScheduleDao targetDao = daoSession.getScheduleDao();
            List<Schedule> schedulesNew = targetDao
                    ._queryScheduleType_Schedules(id);
            synchronized (this) {
                if (schedules == null) {
                    schedules = schedulesNew;
                }
            }
        }
        return schedules;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 283382071)
    public synchronized void resetSchedules() {
        schedules = null;
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
    @Generated(hash = 632208713)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getScheduleTypeDao() : null;
    }
}
