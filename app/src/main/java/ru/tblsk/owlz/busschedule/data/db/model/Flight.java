package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity(active = true)
public class Flight{
    @Expose
    @SerializedName("id")
    @Property(nameInDb = "flight_id")
    @Id
    private Long id;

    @Expose
    @SerializedName("flight_type_fk")
    @Property(nameInDb = "flight_type")
    @NotNull
    @Convert(converter = FlightTypeConverter.class, columnType = Integer.class)
    private FlightType flightType;

    @Expose
    @SerializedName("flight_number")
    @Property(nameInDb = "flight_number")
    @NotNull
    private String flightNumber;

    @ToMany(referencedJoinProperty = "flightId")
    private List<Direction> directions;

    @Generated(hash = 1944495751)
    public Flight(Long id, @NotNull FlightType flightType,
            @NotNull String flightNumber) {
        this.id = id;
        this.flightType = flightType;
        this.flightNumber = flightNumber;
    }

    @Generated(hash = 351578258)
    public Flight() {
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1011557899)
    private transient FlightDao myDao;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlightType getFlightType() {
        return this.flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1386518190)
    public List<Direction> getDirections() {
        if (directions == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DirectionDao targetDao = daoSession.getDirectionDao();
            List<Direction> directionsNew = targetDao._queryFlight_Directions(id);
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
    @Generated(hash = 1317923404)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFlightDao() : null;
    }
}
