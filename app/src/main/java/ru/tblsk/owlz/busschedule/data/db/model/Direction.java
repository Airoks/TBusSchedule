package ru.tblsk.owlz.busschedule.data.db.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true)
public class Direction {
    @Expose
    @SerializedName("id")
    @Property(nameInDb = "direction_id")
    @Id
    private long id;

    @Expose
    @SerializedName("flight_fk")
    @Property(nameInDb = "flight_fk")
    @NotNull
    private long flightId;

    @Expose
    @SerializedName("direction_type_fk")
    @Property(nameInDb = "direction_type_fk")
    @NotNull
    private long directionTypeId;

    @Expose
    @SerializedName("direction_name")
    @Property(nameInDb = "direction_name")
    @NotNull
    private String directionName;

    @ToMany(referencedJoinProperty = "directionId")
    @OrderBy("stopPosition ASC")
    private List<StopsOnRouts> stopsOnRouts;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1467317006)
    private transient DirectionDao myDao;

    @Generated(hash = 473241049)
    public Direction(long id, long flightId, long directionTypeId,
            @NotNull String directionName) {
        this.id = id;
        this.flightId = flightId;
        this.directionTypeId = directionTypeId;
        this.directionName = directionName;
    }

    @Generated(hash = 1390953800)
    public Direction() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlightId() {
        return this.flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public long getDirectionTypeId() {
        return this.directionTypeId;
    }

    public void setDirectionTypeId(long directionTypeId) {
        this.directionTypeId = directionTypeId;
    }

    public String getDirectionName() {
        return this.directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1568510142)
    public List<StopsOnRouts> getStopsOnRouts() {
        if (stopsOnRouts == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StopsOnRoutsDao targetDao = daoSession.getStopsOnRoutsDao();
            List<StopsOnRouts> stopsOnRoutsNew = targetDao
                    ._queryDirection_StopsOnRouts(id);
            synchronized (this) {
                if (stopsOnRouts == null) {
                    stopsOnRouts = stopsOnRoutsNew;
                }
            }
        }
        return stopsOnRouts;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 409902519)
    public synchronized void resetStopsOnRouts() {
        stopsOnRouts = null;
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
    @Generated(hash = 1082205904)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDirectionDao() : null;
    }

}
