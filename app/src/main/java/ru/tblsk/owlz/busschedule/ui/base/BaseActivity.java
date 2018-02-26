package ru.tblsk.owlz.busschedule.ui.base;

import android.support.v7.app.AppCompatActivity;

import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.di.component.ActivityComponent;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;


public abstract class BaseActivity extends AppCompatActivity implements MvpView {
    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        if(this.activityComponent == null) {
            activityComponent = App.getApplicationComponent
                    .activityComponent(new ActivityModule(this));
        }
        return this.activityComponent;
    }
}
