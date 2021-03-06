package ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes;


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
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.SuburbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.UrbanBusRoutes;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.component.BusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.directioninfoscreen.DirectionInfoFragment;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;

public class BusRoutesFragment extends BaseFragment
        implements BusRoutesContract.View {

    public static final String TAG = "BusRoutesFragment";
    public static final String FRAGMENT_ID = "fragmentId";
    public static final String FLIGHT_TYPE = "flightType";
    public static final int URBAN = 0;
    public static final int SUBURBAN = 1;


    @Inject
    LinearLayoutManager mLayoutManager;


    @BindView(R.id.recyclerview_routes)
    RecyclerView mRecyclerView;

    private BusRoutesContract.Presenter mPresenter;
    private BusRoutesAdapter mAdapter;
    private long mFragmentId;

    public static BusRoutesFragment newInstance(long fragmentId, int flightType) {
        Bundle args = new Bundle();
        args.putLong(FRAGMENT_ID, fragmentId);
        args.putInt(FLIGHT_TYPE, flightType);
        BusRoutesFragment fragment = new BusRoutesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    public void setPresenter(@UrbanBusRoutes BusRoutesContract.Presenter urbanPresenter,
                             @SuburbanBusRoutes BusRoutesContract.Presenter suburbanPresenter) {
        int type = getArguments().getInt(FLIGHT_TYPE);
        if(type == URBAN) {
            mPresenter = urbanPresenter;
        } else if(type == SUBURBAN) {
            mPresenter = suburbanPresenter;
        }
    }

    @Inject
    public void setAdapter(@UrbanBusRoutes BusRoutesAdapter urbanAdapter,
                           @SuburbanBusRoutes BusRoutesAdapter suburbanAdapter) {
        int type = getArguments().getInt(FLIGHT_TYPE);
        if(type == URBAN) {
            mAdapter = urbanAdapter;
        } else if(type == SUBURBAN) {
            mAdapter = suburbanAdapter;
        }
    }

    @Override
    protected void setComponent() {
        ComponentManager componentManager = App.getApp(getContext()).getComponentManager();
        BusRoutesScreenComponent component = componentManager.getBusRoutesScreenComponent(mFragmentId);
        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentId = getArguments().getLong(FRAGMENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mPresenter.unsubscribe();
        super.onDestroyView();
    }

    @Override
    protected void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
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
        transaction.addToBackStack(BusRoutesFragment.TAG);
        transaction.commit();
    }
}
