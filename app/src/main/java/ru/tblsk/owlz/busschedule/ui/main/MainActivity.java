package ru.tblsk.owlz.busschedule.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseActivity;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerFragment;
import ru.tblsk.owlz.busschedule.ui.stops.historystops.StopsFragment;

public class MainActivity extends BaseActivity {

    public static final String CURRENT_PAGE_ID = "currentPageId";

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

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUp();
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
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            switch (item.getItemId()) {
                                case R.id.navigation_main:
                                    return true;
                                case R.id.navigation_stops:
                                    fragmentTransaction.replace(R.id.container,
                                            StopsFragment.newInstance());
                                    fragmentTransaction.commit();
                                    return true;
                                case R.id.navigation_routs:
                                    fragmentTransaction.replace(R.id.container,
                                            RoutesContainerFragment.newInstance());
                                    fragmentTransaction.commit();
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
                            case R.id.nav_camera:
                                return true;
                            case R.id.nav_gallery:
                                return true;
                            case R.id.nav_slideshow:
                                return true;
                            case R.id.nav_manage:
                                return true;
                            case R.id.nav_share:
                                return true;
                            case R.id.nav_send:
                                return true;
                        }
                        return false;
                    }
        });
    }
}
