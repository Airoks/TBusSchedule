package ru.tblsk.owlz.busschedule.data.db.model;



import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity(active = true)
public class Direction implements Parcelable{
    @Expose
    @SerializedName("id")
    @Property(nameInDb = "direction_id")
    @Id
    private Long id;

    @Expose
    @SerializedName("flight_fk")
    @Property(nameInDb = "flight_fk")
    @NotNull
    private Long flightId;

    @Expose
    @SerializedName("direction_type_fk")
    @Property(nameInDb = "direction_type_fk")
    @NotNull
    @Convert(converter = DirectionTypeConverter.class, columnType = Integer.class)
    private DirectionType directionType;

    @Expose
    @SerializedName("direction_name")
    @Property(nameInDb = "direction_name")
    @NotNull
    private String directionName;

    @ToMany(referencedJoinProperty = "directionId")
    @OrderBy("stopPosition ASC")
    private List<StopsOnRouts> stopsOnRouts;

    protected Direction(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            flightId = null;
        } else {
            flightId = in.readLong();
        }
        if (in.readByte() == 0) {
            directionType = null;
        } else {
            directionType = DirectionType.valueOf(in.readString());
        }
        directionName = in.readString();
    }

    @Generated(hash = 1233092637)
    public Direction(Long id, @NotNull Long flightId,
            @NotNull DirectionType directionType, @NotNull String directionName) {
        this.id = id;
        this.flightId = flightId;
        this.directionType = directionType;
        this.directionName = directionName;
    }

    @Generated(hash = 1390953800)
    public Direction() {
    }

    public static final Creator<Direction> CREATOR = new Creator<Direction>() {
        @Override
        public Direction createFromParcel(Parcel in) {
            return new Direction(in);
        }

        @Override
        public Direction[] newArray(int size) {
            return new Direction[size];
        }
    };

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1467317006)
    private transient DirectionDao myDao;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        if (flightId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(flightId);
        }
        if (directionType == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeString(directionType.name());
        }
        parcel.writeString(directionName);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlightId() {
        return this.flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public DirectionType getDirectionType() {
        return this.directionType;
    }

    public void setDirectionType(DirectionType directionType) {
        this.directionType = directionType;
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
