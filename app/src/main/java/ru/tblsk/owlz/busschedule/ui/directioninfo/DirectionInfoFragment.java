package ru.tblsk.owlz.busschedule.ui.directioninfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;

public class DirectionInfoFragment extends BaseFragment{

    public static final String TAG = "DirectionInfoFragment";

    public static DirectionInfoFragment newInstance() {
        return new DirectionInfoFragment();
    }

    @Override
    protected void setUp(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direction_info, container, false);
        return view;
    }
}
