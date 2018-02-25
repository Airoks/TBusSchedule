package ru.tblsk.owlz.busschedule.di.module;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.di.annotation.ActivityContext;

@Module
public class ActivityModule {
    private AppCompatActivity appCompatActivity;

    ActivityModule(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Provides
    @ActivityContext
    public Context provideContext() {
        return this.appCompatActivity;
    }

    @Provides
    public AppCompatActivity provideActivity() {
        return this.appCompatActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
