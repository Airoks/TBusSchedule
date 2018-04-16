package ru.tblsk.owlz.busschedule.ui.schedules;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.component.AllBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.BusScheduleScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.BusStopInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.DaggerBusScheduleScreenComponent;
import ru.tblsk.owlz.busschedule.di.module.BusScheduleScreenModule;
import ru.tblsk.owlz.busschedule.di.module.BusStopInfoScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.ui.routes.AllScreenPagerAdapter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleFragment;
import ru.tblsk.owlz.busschedule.utils.CommonUtils;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;

public class ScheduleContainerFragment extends BaseFragment
        implements ScheduleContainerContract.View, SetupToolbar{

    public static final String STOP = "stop";
    public static final String FLIGHT = "flight";
    public static final int WORKDAY = 0;
    public static final int WEEKEND = 1;
    public static final String FRAGMENT_ID = "fragmentId";

    @Inject
    AllScreenPagerAdapter mPagerAdapter;

    @Inject
    ScheduleContainerContract.Presenter mPresenter;

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
    private long mFragmentId;
    private ComponentManager mComponentManager;

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

        mFragmentId = savedInstanceState != null ? savedInstanceState.getLong(FRAGMENT_ID) :
                CommonUtils.NEXT_ID.getAndIncrement();

        mStop = getArguments().getParcelable(STOP);
        mFlight = getArguments().getParcelable(FLIGHT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedulecontainer, container, false);

        mComponentManager = App.getApp(getContext()).getComponentManager();
        BusScheduleScreenComponent component = mComponentManager
                .getBusScheduleScreenComponent(mFragmentId);
        if(component == null) {
            component = mComponentManager.getNewBusScheduleScreenComponent();
            mComponentManager.putBusScheduleScreenComponent(mFragmentId, component);
        }

        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);

        setUnbinder(ButterKnife.bind(this, view));
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle();

        if(getView() != null) {
            getView().setFocusableInTouchMode(true);
            getView().setFocusable(true);
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if(keyEvent.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        mComponentManager.removeBusScheduleScreenComponent(mFragmentId);
                        return  true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(FRAGMENT_ID, mFragmentId);
        super.onSaveInstanceState(outState);
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
                directionId, WORKDAY, mFragmentId), getString(R.string.schedule_workday));
        mPagerAdapter.addFragments(ScheduleFragment.newInstance(stopId,
                directionId, WEEKEND, mFragmentId), getString(R.string.schedule_weekend));
        viewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void setToolbarTitle() {
        mToolbar.setTitle(R.string.schedule);
    }

    @Override
    public void openPreviousFragment() {
        mComponentManager.removeBusScheduleScreenComponent(mFragmentId);
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
