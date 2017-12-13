package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true)
public class Schedule {
    @Id
    private long SchedulePK;

    private long StopsOnRoutsFK;

    private long ScheduleTypeFK;

    @NotNull
    private String ScheduleJson;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1493574644)
    private transient ScheduleDao myDao;

    @Generated(hash = 321905488)
    public Schedule(long SchedulePK, long StopsOnRoutsFK, long ScheduleTypeFK,
            @NotNull String ScheduleJson) {
        this.SchedulePK = SchedulePK;
        this.StopsOnRoutsFK = StopsOnRoutsFK;
        this.ScheduleTypeFK = ScheduleTypeFK;
        this.ScheduleJson = ScheduleJson;
    }

    @Generated(hash = 729319394)
    public Schedule() {
    }

    public long getSchedulePK() {
        return this.SchedulePK;
    }

    public void setSchedulePK(long SchedulePK) {
        this.SchedulePK = SchedulePK;
    }

    public long getStopsOnRoutsFK() {
        return this.StopsOnRoutsFK;
    }

    public void setStopsOnRoutsFK(long StopsOnRoutsFK) {
        this.StopsOnRoutsFK = StopsOnRoutsFK;
    }

    public long getScheduleTypeFK() {
        return this.ScheduleTypeFK;
    }

    public void setScheduleTypeFK(long ScheduleTypeFK) {
        this.ScheduleTypeFK = ScheduleTypeFK;
    }

    public String getScheduleJson() {
        return this.ScheduleJson;
    }

    public void setScheduleJson(String ScheduleJson) {
        this.ScheduleJson = ScheduleJson;
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
