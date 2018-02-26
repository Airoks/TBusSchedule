package ru.tblsk.owlz.busschedule;

import android.app.Application;

import javax.inject.Inject;

import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.di.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.module.ApplicationModule;


public class App extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.build()
                .applicationModule(new ApplicationModule(this)).buid();
    }

    public ApplicationComponent getApplicationComponent() {
        return  this.applicationComponent;
    }

}
