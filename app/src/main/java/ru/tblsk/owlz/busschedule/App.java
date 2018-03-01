package ru.tblsk.owlz.busschedule;

import android.app.Application;
import android.content.Context;

import ru.tblsk.owlz.busschedule.di.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.component.DaggerApplicationComponent;
import ru.tblsk.owlz.busschedule.di.module.ApplicationModule;


public class App extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return  this.mApplicationComponent;
    }

}
