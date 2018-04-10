package ru.tblsk.owlz.busschedule.ui.schedules.schedule;


import android.os.Bundle;
import android.view.View;

import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;

public class ScheduleFragment extends BaseFragment implements ScheduleMvpView{

    public static final String STOP_ID = "stopId";
    public static final String DIRECTION_ID = "directionId";
    public static final String SCHEDULE_TYPE = "scheduleType";

    public static ScheduleFragment newInstance(long stopId, long directionId, int scheduleType) {
        Bundle args = new Bundle();
        args.putLong(STOP_ID, stopId);
        args.putLong(DIRECTION_ID, directionId);
        args.putInt(SCHEDULE_TYPE, scheduleType);
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void showSchedule(List<DepartureTime> times) {

    }
}
