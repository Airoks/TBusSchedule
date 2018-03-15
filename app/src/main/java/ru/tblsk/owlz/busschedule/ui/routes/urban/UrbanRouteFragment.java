package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;

public class UrbanRouteFragment extends BaseFragment{

    public static UrbanRouteFragment newInstance() {
        Bundle args = new Bundle();
        UrbanRouteFragment fragment = new UrbanRouteFragment();
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
