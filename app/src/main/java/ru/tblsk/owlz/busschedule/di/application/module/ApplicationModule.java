package ru.tblsk.owlz.busschedule.di.application.module;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.AppDataManager;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.AppDbHelper;
import ru.tblsk.owlz.busschedule.data.db.DbHelper;
import ru.tblsk.owlz.busschedule.data.preferences.AppPreferencesHelper;
import ru.tblsk.owlz.busschedule.data.preferences.PreferencesHelper;
import ru.tblsk.owlz.busschedule.di.application.ApplicationContext;
import ru.tblsk.owlz.busschedule.di.application.DatabaseInfo;
import ru.tblsk.owlz.busschedule.di.application.PreferencesInfo;
import ru.tblsk.owlz.busschedule.ui.splashscreen.SplashContract;
import ru.tblsk.owlz.busschedule.ui.splashscreen.SplashPresenter;
import ru.tblsk.owlz.busschedule.utils.AppConstants;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.AppSchedulerProvider;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @PreferencesInfo
    String providePreferencesName() {
        return AppConstants.PREFERENCES_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return  appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideScheduler() {
        return new AppSchedulerProvider();
    }

    @Provides
    FlightMapper provideFlightMapper() {
        return new FlightMapper();
    }

    @Provides
    StopMapper provideStopMapper() {
        return new StopMapper();
    }

    @Provides
    DepartureTimeMapper provideDepartureTimeMapper() {
        return new DepartureTimeMapper();
    }

    @Provides
    SplashContract.Presenter provideSplashPresenter(SplashPresenter presenter) {
        return presenter;
    }
}
