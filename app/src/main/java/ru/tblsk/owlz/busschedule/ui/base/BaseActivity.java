package ru.tblsk.owlz.busschedule.ui.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.util.LongSparseArray;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicLong;

import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.component.ActivityComponent;
import ru.tblsk.owlz.busschedule.di.component.ConfigPersistentComponent;
import ru.tblsk.owlz.busschedule.di.component.DaggerConfigPersistentComponent;
import ru.tblsk.owlz.busschedule.di.module.ActivityModule;
import ru.tblsk.owlz.busschedule.di.module.ConfigPersistentModule;


public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback {

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
                    .configPersistentModule(new ConfigPersistentModule())
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

    public ActivityComponent getActivityComponent() {
        return this.mActivityComponent;
    }

    public void showSnackBar(String message) {
        //android.R.id.content - получаем корневое представление Activity
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.black));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if(message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.error));
        }
    }
}
