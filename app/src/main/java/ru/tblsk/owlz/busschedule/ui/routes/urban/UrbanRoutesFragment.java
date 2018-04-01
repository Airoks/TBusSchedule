package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.di.annotation.Type;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoFragment;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesAdapter;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;

public class UrbanRoutesFragment extends BaseFragment
        implements UrbanRoutesMvpView {

    public static final String TAG = "UrbanRoutesFragment";
    public static final String FLIGHTS = "flights";
    public static final String DIRECTION_ROUTS = "directionRouts";
    public static final int DIRECT = DirectionType.DIRECT.id;
    public static final int REVERSE = DirectionType.REVERSE.id;

    @Inject
    UrbanRoutesMvpPresenter<UrbanRoutesMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @Inject
    @Type("urban")
    RoutesAdapter mAdapter;

    @BindView(R.id.urbanRouteRv)
    RecyclerView mRecyclerView;

    private List<FlightVO> mFlights;
    private List<Integer> mDirectionRoutes;

    public static UrbanRoutesFragment newInstance() {
        Bundle args = new Bundle();
        UrbanRoutesFragment fragment = new UrbanRoutesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if(savedInstanceState != null) {
            mFlights = savedInstanceState.getParcelableArrayList(FLIGHTS);
            mDirectionRoutes = savedInstanceState.getIntegerArrayList(DIRECTION_ROUTS);
        }
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
        View view = getView() != null ? getView() :
                inflater.inflate(R.layout.fragment_urbanroutes, container, false);

        getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);

        setUnbinder(ButterKnife.bind(this, view));
        mPresenter.attachView(this);

        if(mFlights == null || savedInstanceState != null) {
            mPresenter.subscribeOnEvents();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FLIGHTS, (ArrayList<? extends Parcelable>) mFlights);
        outState.putIntegerArrayList(DIRECTION_ROUTS, (ArrayList<Integer>) mDirectionRoutes);

    }

    @Override
    protected void setUp(View view) {
        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mAdapter);

        ((MainActivity)getBaseActivity()).unlockDrawer();
        ((MainActivity)getBaseActivity()).showBottomNavigationView();

        if(mFlights == null) {
            mPresenter.getUrbanFlights();
        } else {
            mPresenter.getSavedUrbanFlights();
        }
    }

    @Override
    public void showUrbanRoutes(List<FlightVO> flights) {
        mFlights = flights;
        mDirectionRoutes = new ArrayList<>();
        //при первом запуске пользователь видит прямое направление
        for(int i = 0; i < mFlights.size(); i++) {
            mDirectionRoutes.add(DIRECT);
        }
        mAdapter.addItems(flights, mDirectionRoutes);
    }

    @Override
    public void showSavedUrbanRoutes() {
        mAdapter.addItems(mFlights, mDirectionRoutes);
    }

    @Override
    public void updateDirectionFromDirectionInfo(List<ChangeDirectionUrban.InFragment> inFragments) {
        if(!inFragments.isEmpty()) {
            int position = inFragments.size() - 1;
            mDirectionRoutes.set(inFragments.get(position).getPosition(),
                    inFragments.get(position).getDirectionType());
        }
    }

    @Override
    public void updateDirectionFromAdapter(ChangeDirectionUrban.InAdapter inAdapter) {
        mDirectionRoutes.set(inAdapter.getPosition(), inAdapter.getDirectionType());
    }

    @Override
    public void openDirectionInfoFragment(ChangeDirectionUrban directionUrban) {
        int position = directionUrban.getFlightPosition();

        FragmentManager fragmentManager = getBaseActivity()
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,
                DirectionInfoFragment.newInstance(
                        directionUrban.getDirection(),
                        mFlights.get(position), position));
        transaction.addToBackStack(DirectionInfoFragment.TAG);
        transaction.commit();
    }
}
