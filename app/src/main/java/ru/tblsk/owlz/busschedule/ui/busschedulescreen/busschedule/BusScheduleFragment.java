package ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.WeekendBusSchedule;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.WorkdayBusSchedule;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.component.BusScheduleScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;

public class BusScheduleFragment extends BaseFragment implements BusScheduleContract.View{

    public static final String STOP_ID = "stopId";
    public static final String DIRECTION_ID = "directionId";
    public static final String SCHEDULE_TYPE = "scheduleType";
    public static final int WORKDAY = 0;
    public static final int WEEKEND = 1;
    public static final String FRAGMENT_ID = "fragmentId";

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    BusScheduleAdapter mAdapter;

    @BindView(R.id.recyclerview_schedule)
    RecyclerView mRecyclerView;

    @BindView(R.id.textview_schedule_empty)
    TextView mEmptyTextView;

    BusScheduleContract.Presenter mPresenter;
    private long mFragmentId;
    private ComponentManager mComponentManager;

    public static BusScheduleFragment newInstance(long stopId, long directionId,
                                                  int scheduleType, long fragmentId) {
        Bundle args = new Bundle();
        args.putLong(STOP_ID, stopId);
        args.putLong(DIRECTION_ID, directionId);
        args.putInt(SCHEDULE_TYPE, scheduleType);
        args.putLong(FRAGMENT_ID, fragmentId);
        BusScheduleFragment fragment = new BusScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    public void setPresenter(@WorkdayBusSchedule BusScheduleContract.Presenter workdayPresenter,
                              @WeekendBusSchedule BusScheduleContract.Presenter weekendPresenter) {
        int type = getArguments().getInt(SCHEDULE_TYPE);
        if(type == WORKDAY) {
            mPresenter = workdayPresenter;
        } else if(type == WEEKEND) {
            mPresenter = weekendPresenter;
        }
    }

    @Override
    protected void setComponent() {
        mComponentManager = App.getApp(getContext()).getComponentManager();
        BusScheduleScreenComponent component = mComponentManager.
                getBusScheduleScreenComponent(mFragmentId);
        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentId = getArguments().getLong(FRAGMENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mPresenter.unsubscribe();
        super.onDestroyView();
    }

    @Override
    protected void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        long stopId = getArguments().getLong(STOP_ID);
        long directionId = getArguments().getLong(DIRECTION_ID);
        int scheduleType = getArguments().getInt(SCHEDULE_TYPE);
        mPresenter.attachView(this);
        mPresenter.getSchedule(stopId, directionId, scheduleType);
    }

    @Override
    public void showSchedule(DepartureTimeVO times) {
        mAdapter.addItems(times);
    }

    @Override
    public void showEmptyScreen() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyTextView.setVisibility(View.VISIBLE);
        int scheduleType = getArguments().getInt(SCHEDULE_TYPE);
        if(scheduleType == WORKDAY) {
            mEmptyTextView.setText(getString(R.string.schedule_empty_workday));
        } else if(scheduleType == WEEKEND) {
            mEmptyTextView.setText(getString(R.string.schedule_empty_weekend));
        }
    }
}
