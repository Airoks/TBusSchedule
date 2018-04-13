package ru.tblsk.owlz.busschedule.ui.schedules;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.ui.routes.AllScreenPagerAdapter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleFragment;

public class ScheduleContainerFragment extends BaseFragment
        implements ScheduleContainerMvpView, SetupToolbar{

    public static final String STOP = "stop";
    public static final String FLIGHT = "flight";
    public static final int WORKDAY = 0;
    public static final int WEEKEND = 1;

    @Inject
    AllScreenPagerAdapter mPagerAdapter;

    @Inject
    ScheduleContainerMvpPresenter<ScheduleContainerMvpView> mPresenter;

    @BindView(R.id.toolbar_schedulecontainer)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_schedulecontainer)
    ViewPager mViewPager;

    @BindView(R.id.tablayout_schedulecontainer)
    TabLayout mTabLayout;

    @BindView(R.id.textview_schedulecontainer_routenumber)
    TextView mRouteNumber;

    @BindView(R.id.textview_schedulecontainer_routename)
    TextView mRouteName;

    @BindView(R.id.textview_schedulecontainer_stopname)
    TextView mStopName;

    private FlightVO mFlight;
    private StopVO mStop;

    public static ScheduleContainerFragment newInstance(StopVO stop, FlightVO flight) {
        Bundle args = new Bundle();
        args.putParcelable(STOP, stop);
        args.putParcelable(FLIGHT, flight);
        ScheduleContainerFragment fragment = new ScheduleContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStop = getArguments().getParcelable(STOP);
        mFlight = getArguments().getParcelable(FLIGHT);
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
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        setupToolbar();
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

        mRouteNumber.setText(mFlight.getFlightNumber());
        mRouteName.setText(mFlight.getCurrentDirection().getDirectionName());
        mStopName.setText(mStop.getStopName());

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
                mPresenter.clickedOnBackButton();
            }
        });
    }

    public void setupViewPager(ViewPager viewPager) {
        long stopId = mStop.getId();
        long directionId = mFlight.getCurrentDirection().getId();

        mPagerAdapter.addFragments(ScheduleFragment.newInstance(stopId,
                directionId, WORKDAY), getString(R.string.schedule_workday));
        mPagerAdapter.addFragments(ScheduleFragment.newInstance(stopId,
                directionId, WEEKEND), getString(R.string.schedule_weekend));
        viewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void setToolbarTitle() {
        mToolbar.setTitle(R.string.schedule);
    }

    @Override
    public void openPreviousFragment() {
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
