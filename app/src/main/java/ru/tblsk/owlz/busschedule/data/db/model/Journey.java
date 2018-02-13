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
public class Journey {
    @SerializedName("id")
    @Id
    private long journeyId;

    @SerializedName("journey_type_fk")
    private long journeyTypeId;

    @SerializedName("journey_number")
    @NotNull
    private String journeyNumber;

    @ToMany(referencedJoinProperty = "journeyId")
    private List<Direction> directions;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1285991799)
    private transient JourneyDao myDao;

    @Generated(hash = 440850978)
    public Journey(long journeyId, long journeyTypeId, @NotNull String journeyNumber) {
        this.journeyId = journeyId;
        this.journeyTypeId = journeyTypeId;
        this.journeyNumber = journeyNumber;
    }

    @Generated(hash = 411349523)
    public Journey() {
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 258309062)
    public List<Direction> getDirections() {
        if (directions == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DirectionDao targetDao = daoSession.getDirectionDao();
            List<Direction> directionsNew = targetDao._queryJourney_Directions(journeyId);
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

    public long getJourneyId() {
        return this.journeyId;
    }

    public void setJourneyId(long journeyId) {
        this.journeyId = journeyId;
    }

    public long getJourneyTypeId() {
        return this.journeyTypeId;
    }

    public void setJourneyTypeId(long journeyTypeId) {
        this.journeyTypeId = journeyTypeId;
    }

    public String getJourneyNumber() {
        return this.journeyNumber;
    }

    public void setJourneyNumber(String journeyNumber) {
        this.journeyNumber = journeyNumber;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1567024630)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getJourneyDao() : null;
    }
}
