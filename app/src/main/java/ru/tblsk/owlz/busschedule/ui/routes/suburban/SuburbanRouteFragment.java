package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;

public class SuburbanRouteFragment extends BaseFragment{

    public static SuburbanRouteFragment newInstance() {
        Bundle args = new Bundle();
        SuburbanRouteFragment fragment = new SuburbanRouteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void setUp(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);
        return view;
    }
}