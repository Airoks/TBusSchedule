package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import android.os.Bundle;
import android.view.View;

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
}
