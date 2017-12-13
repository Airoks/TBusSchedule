package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true)
public class Journey {
    @Id
    private long JourneyPK;

    private long JourneyTypeFK;

    @NotNull
    private String JourneyNumber;

    @ToMany(referencedJoinProperty = "JourneyFK")
    private List<Direction> directions;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1285991799)
    private transient JourneyDao myDao;

    @Generated(hash = 2125406133)
    public Journey(long JourneyPK, long JourneyTypeFK,
            @NotNull String JourneyNumber) {
        this.JourneyPK = JourneyPK;
        this.JourneyTypeFK = JourneyTypeFK;
        this.JourneyNumber = JourneyNumber;
    }

    @Generated(hash = 411349523)
    public Journey() {
    }

    public long getJourneyPK() {
        return this.JourneyPK;
    }

    public void setJourneyPK(long JourneyPK) {
        this.JourneyPK = JourneyPK;
    }

    public long getJourneyTypeFK() {
        return this.JourneyTypeFK;
    }

    public void setJourneyTypeFK(long JourneyTypeFK) {
        this.JourneyTypeFK = JourneyTypeFK;
    }

    public String getJourneyNumber() {
        return this.JourneyNumber;
    }

    public void setJourneyNumber(String JourneyNumber) {
        this.JourneyNumber = JourneyNumber;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 220564001)
    public List<Direction> getDirections() {
        if (directions == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DirectionDao targetDao = daoSession.getDirectionDao();
            List<Direction> directionsNew = targetDao
                    ._queryJourney_Directions(JourneyPK);
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
    @Generated(hash = 1567024630)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getJourneyDao() : null;
    }
}
