package ru.tblsk.owlz.busschedule.ui.schedules;


import android.os.Bundle;
import android.view.View;

import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;

public class ScheduleContainerFragment extends BaseFragment{

    public static ScheduleContainerFragment newInstance() {
        Bundle args = new Bundle();
        ScheduleContainerFragment fragment = new ScheduleContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUp(View view) {

    }
}
