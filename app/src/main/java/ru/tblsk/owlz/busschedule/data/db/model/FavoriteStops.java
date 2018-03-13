package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(active = true)
public class FavoriteStops {
    @Id(autoincrement = true)
    @Property(nameInDb = "favorite_stops_id")
    private Long id;

    @Property(nameInDb = "stops_on_routs_fk")
    @NotNull
    private  Long stopsOnRoutsId;

    @ToOne(joinProperty = "stopsOnRoutsId")
    private StopsOnRouts stopsOnRouts;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 986649151)
    private transient FavoriteStopsDao myDao;

    @Generated(hash = 950750758)
    public FavoriteStops(Long id, @NotNull Long stopsOnRoutsId) {
        this.id = id;
        this.stopsOnRoutsId = stopsOnRoutsId;
    }

    @Generated(hash = 1306421919)
    public FavoriteStops() {
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

    @Generated(hash = 454586668)
    private transient Long stopsOnRouts__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 686186327)
    public StopsOnRouts getStopsOnRouts() {
        Long __key = this.stopsOnRoutsId;
        if (stopsOnRouts__resolvedKey == null
                || !stopsOnRouts__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StopsOnRoutsDao targetDao = daoSession.getStopsOnRoutsDao();
            StopsOnRouts stopsOnRoutsNew = targetDao.load(__key);
            synchronized (this) {
                stopsOnRouts = stopsOnRoutsNew;
                stopsOnRouts__resolvedKey = __key;
            }
        }
        return stopsOnRouts;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1716570295)
    public void setStopsOnRouts(@NotNull StopsOnRouts stopsOnRouts) {
        if (stopsOnRouts == null) {
            throw new DaoException(
                    "To-one property 'stopsOnRoutsId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.stopsOnRouts = stopsOnRouts;
            stopsOnRoutsId = stopsOnRouts.getId();
            stopsOnRouts__resolvedKey = stopsOnRoutsId;
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
    @Generated(hash = 144552758)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFavoriteStopsDao() : null;
    }
}
