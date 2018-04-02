package ru.tblsk.owlz.busschedule.ui.routes.suburban;


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

public class SuburbanRoutesFragment extends BaseFragment
        implements SuburbanRoutesMvpView {

    public static final String TAG = "SuburbanRoutesFragment";
    public static final String FLIGHTS = "flights";
    public static final String DIRECTION_ROUTS = "directionRouts";
    public static final int DIRECT = DirectionType.DIRECT.id;
    public static final int REVERSE = DirectionType.REVERSE.id;

    @Inject
    SuburbanRoutesMvpPresenter<SuburbanRoutesMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @Inject
    @Type("suburban")
    RoutesAdapter mAdapter;

    @BindView(R.id.suburbanRouteRv)
    RecyclerView mRecyclerView;

    private List<FlightVO> mFlights;
    private List<Integer> mDirectionRoutes;

    public static SuburbanRoutesFragment newInstance() {
        Bundle args = new Bundle();
        SuburbanRoutesFragment fragment = new SuburbanRoutesFragment();
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

    @Override
    protected void setUp(View view) {
        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mAdapter);

        ((MainActivity)getBaseActivity()).unlockDrawer();
        ((MainActivity)getBaseActivity()).showBottomNavigationView();

        if(mFlights == null) {
            mPresenter.getSuburbanFlights();
        } else {
            mPresenter.getSavedSuburbanFlights();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suburbanroutes, container, false);
        getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);
        setUnbinder(ButterKnife.bind(this, view));
        mPresenter.attachView(this);

        mPresenter.subscribeOnEvents();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FLIGHTS, (ArrayList<? extends Parcelable>) mFlights);
        outState.putIntegerArrayList(DIRECTION_ROUTS, (ArrayList<Integer>) mDirectionRoutes);
    }

    @Override
    public void showSuburbanRoutes(List<FlightVO> flights) {
        mFlights = flights;
        mDirectionRoutes = new ArrayList<>();
        //при первом запуске пользователь видит прямое направление
        for(int i = 0; i < mFlights.size(); i++) {
            mDirectionRoutes.add(DIRECT);
        }
        mAdapter.addItems(flights, mDirectionRoutes);
    }

    @Override
    public void showSavedSuburbanRoutes() {
        mAdapter.addItems(mFlights, mDirectionRoutes);
    }

    @Override
    public void updateDirectionFromDirectionInfo(List<ChangeDirectionSuburban.InFragment> inFragments) {
        if(!inFragments.isEmpty()) {
            int position = inFragments.size() - 1;
            mDirectionRoutes.set(inFragments.get(position).getPosition(),
                    inFragments.get(position).getDirectionType());
        }
    }

    @Override
    public void updateDirectionFromAdapter(ChangeDirectionSuburban.InAdapter inAdapter) {
        mDirectionRoutes.set(inAdapter.getPosition(), inAdapter.getDirectionType());
    }

    @Override
    public void openDirectionInfoFragment(ChangeDirectionSuburban directionSuburban) {
        int position = directionSuburban.getFlightPosition();

        FragmentManager fragmentManager = getBaseActivity()
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,
                DirectionInfoFragment.newInstance(
                        directionSuburban.getDirection(),
                        mFlights.get(position), position));
        transaction.addToBackStack(DirectionInfoFragment.TAG);
        transaction.commit();
    }
}
