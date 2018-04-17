package ru.tblsk.owlz.busschedule.data.db;


import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.tblsk.owlz.busschedule.data.db.model.DaoMaster;
import ru.tblsk.owlz.busschedule.di.application.ApplicationContext;
import ru.tblsk.owlz.busschedule.di.application.DatabaseInfo;

@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper{
    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database database, int oldVersion, int newVersion) {
        super.onUpgrade(database, oldVersion, newVersion);
        //later
    }
}
