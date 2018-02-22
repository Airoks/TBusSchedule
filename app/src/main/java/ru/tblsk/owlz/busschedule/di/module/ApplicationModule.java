package ru.tblsk.owlz.busschedule.di.module;


import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
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
}
