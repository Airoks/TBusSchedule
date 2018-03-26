package ru.tblsk.owlz.busschedule.data.db.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

@Entity(active = true)
public class Schedule {
    @Expose
    @SerializedName("id")
    @Property(nameInDb = "schedule_id")
    @Id
    private Long id;

    @Expose
    @SerializedName("stops_on_routs_fk")
    @Property(nameInDb = "stops_on_routs_fk")
    @NotNull
    private Long stopsOnRoutsId;

    @Expose
    @SerializedName("schedule_type")
    @Property(nameInDb = "schedule_type")
    @NotNull
    @Convert(converter = ScheduleTypeConverter.class, columnType = Integer.class)
    private ScheduleType scheduleType;

    @ToMany(referencedJoinProperty = "scheduleId")
    private List<DepartureTime> departureTimes;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1493574644)
    private transient ScheduleDao myDao;

    @Generated(hash = 347471495)
    public Schedule(Long id, @NotNull Long stopsOnRoutsId,
            @NotNull ScheduleType scheduleType) {
        this.id = id;
        this.stopsOnRoutsId = stopsOnRoutsId;
        this.scheduleType = scheduleType;
    }

    @Generated(hash = 729319394)
    public Schedule() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStopsOnRoutsId() {
        return this.stopsOnRoutsId;
    }

    public void setStopsOnRoutsId(Long stopsOnRoutsId) {
        this.stopsOnRoutsId = stopsOnRoutsId;
    }

    public ScheduleType getScheduleType() {
        return this.scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 938702560)
    public List<DepartureTime> getDepartureTimes() {
        if (departureTimes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DepartureTimeDao targetDao = daoSession.getDepartureTimeDao();
            List<DepartureTime> departureTimesNew = targetDao
                    ._querySchedule_DepartureTimes(id);
            synchronized (this) {
                if (departureTimes == null) {
                    departureTimes = departureTimesNew;
                }
            }
        }
        return departureTimes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2091103014)
    public synchronized void resetDepartureTimes() {
        departureTimes = null;
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

    public enum ScheduleType {
        @SerializedName("0")
        WORKDAY(0),
        @SerializedName("1")
        WEEKEND(1);

        int id;

        ScheduleType(int id) {
            this.id = id;
        }
    }

    public static class ScheduleTypeConverter
            implements PropertyConverter<ScheduleType, Integer> {

        @Override
        public ScheduleType convertToEntityProperty(Integer databaseValue) {
            for(ScheduleType type : ScheduleType.values()) {
                if(type.id == databaseValue) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public Integer convertToDatabaseValue(ScheduleType entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }


}
