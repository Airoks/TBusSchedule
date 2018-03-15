package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;

public class UrbanRoutesFragment extends BaseFragment
        implements UrbanRoutesMvpView{

    public static final String TAG = "UrbanRoutesFragment";

    @Inject
    UrbanRoutesMvpPresenter<UrbanRoutesMvpView> mPresenter;

    public static UrbanRoutesFragment newInstance() {
        Bundle args = new Bundle();
        UrbanRoutesFragment fragment = new UrbanRoutesFragment();
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
        View view = inflater.inflate(R.layout.fragment_urban_routes, container, false);
        return view;
    }

    @Override
    public void showUrbanRoutes(List<Flight> flights) {

    }
}
