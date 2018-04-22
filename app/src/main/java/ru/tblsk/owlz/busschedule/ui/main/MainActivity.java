package ru.tblsk.owlz.busschedule.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseActivity;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.BusRoutesContainerFragment;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.viewedbusstopsscreen.ViewedBusStopsFragment;
import ru.tblsk.owlz.busschedule.ui.favoritebusstopsscreen.FavoriteBusStopsFragment;

public class MainActivity extends BaseActivity implements MainContract.View {

    public static final String CURRENT_PAGE_ID = "currentPageId";

    @Inject
    MainContract.Presenter mPresenter;

    @BindView(R.id.bottomnavigationview_mainscreen)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.navigationview_main)
    NavigationView mNavigationView;

    private int currentPageId = - 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            currentPageId = savedInstanceState.getInt(CURRENT_PAGE_ID);
        }

        App.getApp(getBaseContext()).getApplicationComponent().inject(this);
        mPresenter.attachView(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUp();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGE_ID, currentPageId);
    }

    @Override
    protected void setUp() {
        setupNavView();
        setupBotNavView();

        if(currentPageId == -1) {
            mBottomNavigationView.setSelectedItemId(R.id.navigation_main);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void openDrawer() {
        mDrawer.openDrawer(Gravity.START);
    }

    public void lockDrawer() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unlockDrawer() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void hideBottomNavigationView() {
        mBottomNavigationView.setVisibility(View.GONE);
    }

    public void showBottomNavigationView() {
        mBottomNavigationView.setVisibility(View.VISIBLE);
    }

    private void setupBotNavView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(currentPageId == item.getItemId()) {
                            return false;
                        } else {
                            currentPageId = item.getItemId();
                            switch (item.getItemId()) {
                                case R.id.navigation_main:
                                    mPresenter.clickedOnFavoriteBusStopsInNavigationView();
                                    return true;
                                case R.id.navigation_stops:
                                    mPresenter.clickedOnViewedBusStopsInNavigationView();
                                    return true;
                                case R.id.navigation_routs:
                                    mPresenter.clickedOnBusRouteInNavigationView();
                                    return true;
                            }
                        }
                        return false;
                    }
                });
    }
    private void setupNavView() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.nav_setting:
                                return true;
                            case R.id.nav_send:
                                return true;
                            case R.id.nav_market:
                                return true;
                            case R.id.nav_share:
                                return true;
                        }
                        return false;
                    }
        });
    }

    @Override
    public void openFavoriteBusStopsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,
                FavoriteBusStopsFragment.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    public void openViewedBusStopsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,
                ViewedBusStopsFragment.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    public void openBusRoutesContainerFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,
                BusRoutesContainerFragment.newInstance());
        fragmentTransaction.commit();
    }
}
