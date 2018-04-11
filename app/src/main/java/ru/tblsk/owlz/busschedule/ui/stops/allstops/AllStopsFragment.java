package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.annotation.Type;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoFragment;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public class AllStopsFragment extends BaseFragment
        implements AllStopsMvpView, SetupToolbar{

    public static final String TAG = "AllStopsFragment";
    public static final String STOPS = "stops";

    @Inject
    AllStopsMvpPresenter<AllStopsMvpView> mPresenter;

    @Inject
    @Type("allstops")
    StopsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.toolbar_allstop)
    Toolbar mToolbar;

    @BindView(R.id.recyclerview_allstop)
    RecyclerView mRecyclerView;

    @BindView(R.id.fastscroll_allstop)
    FastScroller mFastScroller;

    private List<StopVO> mStops;

    public static AllStopsFragment newInstance() {
        return new AllStopsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            mStops = savedInstanceState.getParcelableArrayList(STOPS);
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
    public void showAllStops(List<StopVO> stops) {
        mStops = stops;
        mAdapter.addItems(stops);
    }

    @Override
    public void showSavedAllStops() {
        mAdapter.addItems(mStops);
    }

    @Override
    public void openStopInfoFragment(StopVO stop) {
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, StopInfoFragment.newInstance(stop));
        fragmentTransaction.addToBackStack(AllStopsFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void openPreviousFragment() {
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = getView() != null ? getView() :
                inflater.inflate(R.layout.fragment_allstop, container, false);
        getBaseActivity().getActivityComponent().
                fragmentComponent(new FragmentModule(this)).inject(this);
        mPresenter.attachView(this);
        mPresenter.subscribeOnEvents();
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mToolbar.setTitle(R.string.all_stops);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STOPS, (ArrayList<? extends Parcelable>) mStops);
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mFastScroller.setRecyclerView(mRecyclerView);
        setupToolbar();

        if(mStops == null) {
            mPresenter.getAllStops();
        } else {
            mPresenter.getSavedAllStops();
        }
    }

    @Override
    public void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.all_arrowbackblack_24dp);
        mToolbar.setTitle(R.string.all_stops);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));

        ((MainActivity)getBaseActivity()).lockDrawer();
        ((MainActivity)getBaseActivity()).hideBottomNavigationView();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clickedOnBackButton();
            }
        });
    }
}
