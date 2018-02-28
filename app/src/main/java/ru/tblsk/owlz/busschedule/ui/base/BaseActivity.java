package ru.tblsk.owlz.busschedule.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.util.LongSparseArray;

import java.util.concurrent.atomic.AtomicLong;

import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.di.component.ActivityComponent;
import ru.tblsk.owlz.busschedule.di.component.ConfigPersistentComponent;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;


public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong();
    private static final LongSparseArray<ConfigPersistentComponent>
            sComponent = new LongSparseArray<>();

    private ActivityComponent mActivityComponent;
    private  Long mActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        //if config not change then returned null
        ConfigPersistentComponent configPersistentComponent =
                sComponent.get(mActivityId);

        if(configPersistentComponent == null) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(App.getApp(this).getApplicationComponent())
                    .build();
            sComponent.put(mActivityId, configPersistentComponent);
        }
        mActivityComponent = configPersistentComponent
                .activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if(!isChangingConfigurations()) {
            sComponent.remove(mActivityId);
        }
        super.onDestroy();
    }

    private ActivityComponent getActivityComponent() {
        return this.mActivityComponent;
    }

}
