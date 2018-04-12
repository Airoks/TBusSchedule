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
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.annotation.WeekendSchedule;
import ru.tblsk.owlz.busschedule.di.annotation.WorkdaySchedule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DepartureTimeVO;

public class ScheduleFragment extends BaseFragment implements ScheduleMvpView{

    public static final String STOP_ID = "stopId";
    public static final String DIRECTION_ID = "directionId";
    public static final String SCHEDULE_TYPE = "scheduleType";
    public static final int WORKDAY = 0;
    public static final int WEEKEND = 1;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ScheduleAdapter mAdapter;

    @BindView(R.id.recyclerview_schedule)
    RecyclerView mRecyclerView;

    @BindView(R.id.textview_schedule_empty)
    TextView mEmptyTextView;

    ScheduleMvpPresenter<ScheduleMvpView> mPresenter;

    public static ScheduleFragment newInstance(long stopId, long directionId, int scheduleType) {
        Bundle args = new Bundle();
        args.putLong(STOP_ID, stopId);
        args.putLong(DIRECTION_ID, directionId);
        args.putInt(SCHEDULE_TYPE, scheduleType);
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    public void setPresenter(@WorkdaySchedule ScheduleMvpPresenter<ScheduleMvpView> workdayPresenter,
                              @WeekendSchedule ScheduleMvpPresenter<ScheduleMvpView> weekendPresenter) {
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
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = getView() != null ? getView() :
                inflater.inflate(R.layout.fragment_schedule, container, false);

        getBaseActivity().getActivityComponent().fragmentComponent(new FragmentModule(this))
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
    public void onDestroy() {
        mPresenter.clearData();
        super.onDestroy();
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
        int scheduleType = getArguments().getInt(SCHEDULE_TYPE);
        if(scheduleType == WORKDAY) {
            mEmptyTextView.setText(getString(R.string.schedule_empty_workday));
        } else if(scheduleType == WEEKEND) {
            mEmptyTextView.setText(getString(R.string.schedule_empty_weekend));
        }
    }
}
