package ru.tblsk.owlz.busschedule.ui.schedules;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.routes.AllScreenPagerAdapter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleFragment;

public class ScheduleContainerFragment extends BaseFragment
        implements ScheduleContainerMvpView, SetupToolbar{

    public static final String STOP_ID = "stopId";
    public static final String DIRECTION_ID = "directionId";
    public static final int WORKDAY = 0;
    public static final int WEEKEND = 1;

    @Inject
    AllScreenPagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar_schedulecontainer)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_schedulecontainer)
    ViewPager mViewPager;

    @BindView(R.id.tablayout_schedulecontainer)
    TabLayout mTabLayout;

    public static ScheduleContainerFragment newInstance(long stopId, long directionId) {
        Bundle args = new Bundle();
        args.putLong(STOP_ID, stopId);
        args.putLong(DIRECTION_ID, directionId);
        ScheduleContainerFragment fragment = new ScheduleContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = getView() != null ? getView() :
                inflater.inflate(R.layout.fragment_schedulecontainer, container, false);
        getBaseActivity().getActivityComponent().fragmentComponent(new FragmentModule(this))
                .inject(this);
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle();
    }

    @Override
    protected void setUp(View view) {
        setupToolbar();
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

        ((MainActivity)getBaseActivity()).lockDrawer();
        ((MainActivity)getBaseActivity()).hideBottomNavigationView();
    }

    @Override
    public void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.all_arrowbackblack_24dp);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getBaseActivity()).openDrawer();
            }
        });
    }

    public void setupViewPager(ViewPager viewPager) {
        long stopId = getArguments().getLong(STOP_ID);
        long directionId = getArguments().getLong(DIRECTION_ID);

        mPagerAdapter.addFragments(ScheduleFragment.newInstance(stopId, directionId, WORKDAY),
                getString(R.string.schedule_workday));
        mPagerAdapter.addFragments(ScheduleFragment.newInstance(stopId, directionId, WEEKEND),
                getString(R.string.schedule_weekend));
        viewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void setToolbarTitle() {
        mToolbar.setTitle(R.string.schedule);
    }
}
