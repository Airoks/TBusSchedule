package ru.tblsk.owlz.busschedule.di.module;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.data.AppDataManager;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.AppDbHelper;
import ru.tblsk.owlz.busschedule.data.db.DbHelper;
import ru.tblsk.owlz.busschedule.data.preferences.AppPreferencesHelper;
import ru.tblsk.owlz.busschedule.data.preferences.PreferencesHelper;
import ru.tblsk.owlz.busschedule.di.annotation.ApplicationContext;
import ru.tblsk.owlz.busschedule.di.annotation.DatabaseInfo;
import ru.tblsk.owlz.busschedule.di.annotation.PreferencesInfo;
import ru.tblsk.owlz.busschedule.utils.AppConstants;

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    public Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    public String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @PreferencesInfo
    public String providePreferencesName() {
        return AppConstants.PREFERENCES_NAME;
    }

    @Provides
    @Singleton
    public DataManager provideDataManager(AppDataManager appDataManager) {
        return  appDataManager;
    }

    @Provides
    @Singleton
    public DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    public PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
