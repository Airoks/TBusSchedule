package ru.tblsk.owlz.busschedule.ui.schedules.schedule;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.annotation.WeekendBusSchedule;
import ru.tblsk.owlz.busschedule.di.annotation.WorkdayBusSchedule;
import ru.tblsk.owlz.busschedule.di.component.BusScheduleScreenComponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;

public class ScheduleFragment extends BaseFragment implements ScheduleContract.View{

    public static final String STOP_ID = "stopId";
    public static final String DIRECTION_ID = "directionId";
    public static final String SCHEDULE_TYPE = "scheduleType";
    public static final int WORKDAY = 0;
    public static final int WEEKEND = 1;
    public static final String FRAGMENT_ID = "fragmentId";

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ScheduleAdapter mAdapter;

    @BindView(R.id.recyclerview_schedule)
    RecyclerView mRecyclerView;

    @BindView(R.id.textview_schedule_empty)
    TextView mEmptyTextView;

    ScheduleContract.Presenter mPresenter;
    private long mFragmentId;
    private ComponentManager mComponentManager;

    public static ScheduleFragment newInstance(long stopId, long directionId,
                                               int scheduleType, long fragmentId) {
        Bundle args = new Bundle();
        args.putLong(STOP_ID, stopId);
        args.putLong(DIRECTION_ID, directionId);
        args.putInt(SCHEDULE_TYPE, scheduleType);
        args.putLong(FRAGMENT_ID, fragmentId);
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    public void setPresenter(@WorkdayBusSchedule ScheduleContract.Presenter workdayPresenter,
                              @WeekendBusSchedule ScheduleContract.Presenter weekendPresenter) {
        int type = getArguments().getInt(SCHEDULE_TYPE);
        if(type == WORKDAY) {
            mPresenter = workdayPresenter;
        } else if(type == WEEKEND) {
            mPresenter = weekendPresenter;
        }
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

        mComponentManager = App.getApp(getContext()).getComponentManager();
        BusScheduleScreenComponent component = mComponentManager.
                getBusScheduleScreenComponent(mFragmentId);

        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);



        mPresenter.attachView(this);
        setUnbinder(ButterKnife.bind(this, view));

        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mPresenter.unsubscribeFromEvents();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        long stopId = getArguments().getLong(STOP_ID);
        long directionId = getArguments().getLong(DIRECTION_ID);
        int scheduleType = getArguments().getInt(SCHEDULE_TYPE);
        mPresenter.getSchedule(stopId, directionId, scheduleType);
    }

    @Override
    public void showSchedule(List<DepartureTimeVO> times) {
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
