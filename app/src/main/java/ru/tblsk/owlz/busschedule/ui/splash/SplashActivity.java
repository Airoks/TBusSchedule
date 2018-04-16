package ru.tblsk.owlz.busschedule.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseActivity;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;


public class SplashActivity extends BaseActivity implements SplashContract.View {

   @Inject
   SplashContract.Presenter mPresenter;

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        App.getApp(getBaseContext()).getApplicationComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void setUp() {

    }
}
