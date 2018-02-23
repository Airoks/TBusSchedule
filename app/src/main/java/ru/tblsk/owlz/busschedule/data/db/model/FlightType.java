package ru.tblsk.owlz.busschedule.data.db.model;


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
public class FlightType {
    @SerializedName("id")
    @Property(nameInDb = "flight_type_id")
    @Id
    private long flightTypeId;

    @SerializedName("flight_type_name")
    @Property(nameInDb = "flight_type_name")
    @NotNull
    private String flightTypeName;

    @ToMany(referencedJoinProperty = "flightTypeId")
    private List<Flight> flights;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1377307004)
    private transient FlightTypeDao myDao;

    @Generated(hash = 804054836)
    public FlightType(long flightTypeId, @NotNull String flightTypeName) {
        this.flightTypeId = flightTypeId;
        this.flightTypeName = flightTypeName;
    }

    @Generated(hash = 2059853375)
    public FlightType() {
    }

    public long getFlightTypeId() {
        return this.flightTypeId;
    }

    public void setFlightTypeId(long flightTypeId) {
        this.flightTypeId = flightTypeId;
    }

    public String getFlightTypeName() {
        return this.flightTypeName;
    }

    public void setFlightTypeName(String flightTypeName) {
        this.flightTypeName = flightTypeName;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 862549233)
    public List<Flight> getFlights() {
        if (flights == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FlightDao targetDao = daoSession.getFlightDao();
            List<Flight> flightsNew = targetDao
                    ._queryFlightType_Flights(flightTypeId);
            synchronized (this) {
                if (flights == null) {
                    flights = flightsNew;
                }
            }
        }
        return flights;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1831633566)
    public synchronized void resetFlights() {
        flights = null;
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
    @Generated(hash = 1289739846)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFlightTypeDao() : null;
    }


}
