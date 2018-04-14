package ru.tblsk.owlz.busschedule.ui.routes.route;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.annotation.SuburbanRoutes;
import ru.tblsk.owlz.busschedule.di.annotation.UrbanRoutes;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoFragment;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;

public class RoutesFragment extends BaseFragment
        implements RouteContract.View {

    public static final String TAG = "RoutesFragment";
    public static final String FLIGHT_TYPE = "flightType";
    public static final int URBAN = 0;
    public static final int SUBURBAN = 1;


    @Inject
    LinearLayoutManager mLayoutManager;


    @BindView(R.id.recyclerview_routes)
    RecyclerView mRecyclerView;

    private RouteContract.Presenter mPresenter;
    RoutesAdapter mAdapter;

    public static RoutesFragment newInstance(int flightType) {
        Bundle args = new Bundle();
        args.putInt(FLIGHT_TYPE, flightType);
        RoutesFragment fragment = new RoutesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    public void setPresenter(@UrbanRoutes RouteContract.Presenter urbanPresenter,
                             @SuburbanRoutes RouteContract.Presenter suburbanPresenter) {
        int type = getArguments().getInt(FLIGHT_TYPE);
        if(type == URBAN) {
            mPresenter = urbanPresenter;
        } else if(type == SUBURBAN) {
            mPresenter = suburbanPresenter;
        }
    }

    @Inject
    public void setAdapter(@UrbanRoutes RoutesAdapter urbanAdapter,
                           @SuburbanRoutes RoutesAdapter suburbanAdapter) {
        int type = getArguments().getInt(FLIGHT_TYPE);
        if(type == URBAN) {
            mAdapter = urbanAdapter;
        } else if(type == SUBURBAN) {
            mAdapter = suburbanAdapter;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mPresenter.unsubscribeFromEvents();
        mPresenter.clearData();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);
        getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);
        setUnbinder(ButterKnife.bind(this, view));

        mPresenter.attachView(this);
        mPresenter.subscribeOnEvents();

        return view;
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        ((MainActivity)getBaseActivity()).unlockDrawer();
        ((MainActivity)getBaseActivity()).showBottomNavigationView();

        mPresenter.getFlights();
    }

    @Override
    public void showRoutes(List<FlightVO> flights) {
        mAdapter.addItems(flights);
    }

    @Override
    public void showEmptyScreen() {

    }

    @Override
    public void openDirectionInfoFragment(FlightVO flight) {
        FragmentManager fragmentManager = getBaseActivity()
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,
                DirectionInfoFragment.newInstance(flight));
        transaction.addToBackStack(RoutesFragment.TAG);
        transaction.commit();
    }
}