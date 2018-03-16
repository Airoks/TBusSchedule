package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;

public class SuburbanRoutesFragment extends BaseFragment
        implements SuburbanRoutesMvpView{

    public static final String TAG = "SuburbanRoutesFragment";

    @Inject
    SuburbanRoutesMvpPresenter<SuburbanRoutesMvpView> mPresenter;

    public static SuburbanRoutesFragment newInstance() {
        Bundle args = new Bundle();
        SuburbanRoutesFragment fragment = new SuburbanRoutesFragment();
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
        View view = inflater.inflate(R.layout.fragment_suburban_routes, container, false);
        getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);
        return view;
    }

    @Override
    public void showSuburbanRoutes(List<Flight> flights) {

    }
}
