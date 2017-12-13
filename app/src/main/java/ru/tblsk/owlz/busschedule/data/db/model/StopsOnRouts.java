package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true)
public class StopsOnRouts {
    @Id
    private long StopOnRoutsPK;

    private long DirectionFK;

    private long StopFK;

    private int StopsOnRoutsQuetch;

    @ToMany(referencedJoinProperty = "StopsOnRoutsFK")
    private List<Schedule> schedules;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2025255696)
    private transient StopsOnRoutsDao myDao;

    @Generated(hash = 468729794)
    public StopsOnRouts(long StopOnRoutsPK, long DirectionFK, long StopFK,
            int StopsOnRoutsQuetch) {
        this.StopOnRoutsPK = StopOnRoutsPK;
        this.DirectionFK = DirectionFK;
        this.StopFK = StopFK;
        this.StopsOnRoutsQuetch = StopsOnRoutsQuetch;
    }

    @Generated(hash = 38000403)
    public StopsOnRouts() {
    }

    public long getStopOnRoutsPK() {
        return this.StopOnRoutsPK;
    }

    public void setStopOnRoutsPK(long StopOnRoutsPK) {
        this.StopOnRoutsPK = StopOnRoutsPK;
    }

    public long getDirectionFK() {
        return this.DirectionFK;
    }

    public void setDirectionFK(long DirectionFK) {
        this.DirectionFK = DirectionFK;
    }

    public long getStopFK() {
        return this.StopFK;
    }

    public void setStopFK(long StopFK) {
        this.StopFK = StopFK;
    }

    public int getStopsOnRoutsQuetch() {
        return this.StopsOnRoutsQuetch;
    }

    public void setStopsOnRoutsQuetch(int StopsOnRoutsQuetch) {
        this.StopsOnRoutsQuetch = StopsOnRoutsQuetch;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 944540073)
    public List<Schedule> getSchedules() {
        if (schedules == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScheduleDao targetDao = daoSession.getScheduleDao();
            List<Schedule> schedulesNew = targetDao
                    ._queryStopsOnRouts_Schedules(StopOnRoutsPK);
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
    @Generated(hash = 1395389195)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStopsOnRoutsDao() : null;
    }
}
