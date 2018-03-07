package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true)
public class SearchHistoryStops {
    @Id(autoincrement = true)
    @Property(nameInDb = "search_history_stop_id")
    private long id;

    @Property(nameInDb = "stop_fk")
    @NotNull
    private long stopId;

    @ToOne(joinProperty = "stopId")
    private Stop stop;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1886775999)
    private transient SearchHistoryStopsDao myDao;

    @Generated(hash = 1906981172)
    public SearchHistoryStops(long id, long stopId) {
        this.id = id;
        this.stopId = stopId;
    }

    @Generated(hash = 882008364)
    public SearchHistoryStops() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStopId() {
        return this.stopId;
    }

    public void setStopId(long stopId) {
        this.stopId = stopId;
    }

    @Generated(hash = 2041608776)
    private transient Long stop__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1233391797)
    public Stop getStop() {
        long __key = this.stopId;
        if (stop__resolvedKey == null || !stop__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StopDao targetDao = daoSession.getStopDao();
            Stop stopNew = targetDao.load(__key);
            synchronized (this) {
                stop = stopNew;
                stop__resolvedKey = __key;
            }
        }
        return stop;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1924622999)
    public void setStop(@NotNull Stop stop) {
        if (stop == null) {
            throw new DaoException(
                    "To-one property 'stopId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.stop = stop;
            stopId = stop.getId();
            stop__resolvedKey = stopId;
        }
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
    @Generated(hash = 357236580)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSearchHistoryStopsDao() : null;
    }
}
