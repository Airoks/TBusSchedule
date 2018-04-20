package ru.tblsk.owlz.busschedule;

import android.app.Application;
import android.content.Context;

import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.application.component.DaggerApplicationComponent;
import ru.tblsk.owlz.busschedule.di.application.module.ApplicationModule;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;


public class App extends Application {

    private ApplicationComponent mApplicationComponent;
    private ComponentManager mComponentManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mComponentManager = new ComponentManager(mApplicationComponent);
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return  this.mApplicationComponent;
    }

    public ComponentManager getComponentManager() {
        return this.mComponentManager;
    }

}
