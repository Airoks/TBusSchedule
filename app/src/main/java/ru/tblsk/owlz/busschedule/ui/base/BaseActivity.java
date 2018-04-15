package ru.tblsk.owlz.busschedule.ui.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicLong;

import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;



public abstract class BaseActivity extends AppCompatActivity
        implements MvpView {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong();
    //private static final LongSparseArray<ActivityComponent> sComponent = new LongSparseArray<>();

    //private ActivityComponent mActivityComponent;
    private  Long mActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        //if config not change then returned null
        //ActivityComponent activityComponent = sComponent.get(mActivityId);

       /* if(activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.getApp(this).getApplicationComponent())
                    .build();
            sComponent.put(mActivityId, activityComponent);
        }
        mActivityComponent = activityComponent;*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        /*if(!isChangingConfigurations()) {
            sComponent.remove(mActivityId);
        }*/
        super.onDestroy();
    }

    /*public ActivityComponent getActivityComponent() {
        return this.mActivityComponent;
    }*/

    public void showSnackBar(String message) {
        //android.R.id.content - получаем корневое представление Activity
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
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

    abstract protected void setUp();
}
