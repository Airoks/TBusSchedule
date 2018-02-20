package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true)
public class DirectionType {
    @SerializedName("id")
    @Id
    private long directionTypeId;

    @SerializedName("direction_type_name")
    @NotNull
    private String directionTypeName;

    @ToMany(referencedJoinProperty = "directionTypeId")
    private List<Direction> directions;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 857977871)
    private transient DirectionTypeDao myDao;

    @Generated(hash = 1993002389)
    public DirectionType(long directionTypeId, @NotNull String directionTypeName) {
        this.directionTypeId = directionTypeId;
        this.directionTypeName = directionTypeName;
    }

    @Generated(hash = 85717541)
    public DirectionType() {
    }

    public long getDirectionTypeId() {
        return this.directionTypeId;
    }

    public void setDirectionTypeId(long directionTypeId) {
        this.directionTypeId = directionTypeId;
    }

    public String getDirectionTypeName() {
        return this.directionTypeName;
    }

    public void setDirectionTypeName(String directionTypeName) {
        this.directionTypeName = directionTypeName;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1237214477)
    public List<Direction> getDirections() {
        if (directions == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DirectionDao targetDao = daoSession.getDirectionDao();
            List<Direction> directionsNew = targetDao
                    ._queryDirectionType_Directions(directionTypeId);
            synchronized (this) {
                if (directions == null) {
                    directions = directionsNew;
                }
            }
        }
        return directions;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 513642470)
    public synchronized void resetDirections() {
        directions = null;
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
    @Generated(hash = 325215784)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDirectionTypeDao() : null;
    }


}
