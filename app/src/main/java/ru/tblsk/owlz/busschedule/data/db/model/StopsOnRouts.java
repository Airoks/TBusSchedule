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
public class StopsOnRouts {
    @Expose
    @SerializedName("id")
    @Property(nameInDb = "stop_on_routs_id")
    @Id
    private Long id;

    @Expose
    @SerializedName("direction_fk")
    @Property(nameInDb = "direction_fk")
    @NotNull
    private Long directionId;

    @Expose
    @SerializedName("stop_fk")
    @Property(nameInDb = "stop_fk")
    @NotNull
    private Long stopId;

    @Expose
    @SerializedName("stop_position")
    @Property(nameInDb = "stop_position")
    @NotNull
    private int stopPosition;

    @ToMany(referencedJoinProperty = "stopsOnRoutsId")
    private List<Schedule> schedules;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2025255696)
    private transient StopsOnRoutsDao myDao;

    @Generated(hash = 1219465487)
    public StopsOnRouts(Long id, @NotNull Long directionId, @NotNull Long stopId,
            int stopPosition) {
        this.id = id;
        this.directionId = directionId;
        this.stopId = stopId;
        this.stopPosition = stopPosition;
    }

    @Generated(hash = 38000403)
    public StopsOnRouts() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDirectionId() {
        return this.directionId;
    }

    public void setDirectionId(Long directionId) {
        this.directionId = directionId;
    }

    public Long getStopId() {
        return this.stopId;
    }

    public void setStopId(Long stopId) {
        this.stopId = stopId;
    }

    public int getStopPosition() {
        return this.stopPosition;
    }

    public void setStopPosition(int stopPosition) {
        this.stopPosition = stopPosition;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1752148033)
    public List<Schedule> getSchedules() {
        if (schedules == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ScheduleDao targetDao = daoSession.getScheduleDao();
            List<Schedule> schedulesNew = targetDao
                    ._queryStopsOnRouts_Schedules(id);
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
