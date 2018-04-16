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
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.annotation.SuburbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.annotation.UrbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.component.BusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.DaggerBusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.di.module.BusRoutesScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoFragment;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;

public class RoutesFragment extends BaseFragment
        implements RouteContract.View {

    public static final String TAG = "RoutesFragment";
    public static final String FRAGMENT_ID = "fragmentId";
    public static final String FLIGHT_TYPE = "flightType";
    public static final int URBAN = 0;
    public static final int SUBURBAN = 1;


    @Inject
    LinearLayoutManager mLayoutManager;


    @BindView(R.id.recyclerview_routes)
    RecyclerView mRecyclerView;

    private RouteContract.Presenter mPresenter;
    private RoutesAdapter mAdapter;
    private long mFragmentId;

    public static RoutesFragment newInstance(long fragmentId, int flightType) {
        Bundle args = new Bundle();
        args.putLong(FRAGMENT_ID, fragmentId);
        args.putInt(FLIGHT_TYPE, flightType);
        RoutesFragment fragment = new RoutesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    public void setPresenter(@UrbanBusRoutes RouteContract.Presenter urbanPresenter,
                             @SuburbanBusRoutes RouteContract.Presenter suburbanPresenter) {
        int type = getArguments().getInt(FLIGHT_TYPE);
        if(type == URBAN) {
            mPresenter = urbanPresenter;
        } else if(type == SUBURBAN) {
            mPresenter = suburbanPresenter;
        }
    }

    @Inject
    public void setAdapter(@UrbanBusRoutes RoutesAdapter urbanAdapter,
                           @SuburbanBusRoutes RoutesAdapter suburbanAdapter) {
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
        mFragmentId = getArguments().getLong(FRAGMENT_ID);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mPresenter.unsubscribeFromEvents();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);

        ComponentManager componentManager = App.getApp(getContext()).getComponentManager();
        BusRoutesScreenComponent component = componentManager.getBusRoutesScreenComponent(mFragmentId);
        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);



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
