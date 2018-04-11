package ru.tblsk.owlz.busschedule.ui.directioninfo;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public class DirectionInfoFragment extends BaseFragment
        implements DirectionInfoMvpView, SetupToolbar{

    public static final String TAG = "DirectionInfoFragment";
    public static final String STOPS = "stops";
    public static final String FLIGHT = "flight";

    @Inject
    DirectionInfoMvpPresenter<DirectionInfoMvpView> mPresenter;

    @Inject
    DirectionInfoAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.toolbar_directioninfo)
    Toolbar mToolbar;

    @BindView(R.id.textview_directioninfo_directionname)
    TextView mDirectionName;

    @BindView(R.id.recyclerview_directioninfo_stops)
    RecyclerView mRecyclerView;

    private FlightVO mFlight;
    private List<StopVO> mStops;


    public static DirectionInfoFragment newInstance(@NonNull FlightVO flight) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(FLIGHT, flight);
        DirectionInfoFragment newInstance = new DirectionInfoFragment();
        newInstance.setArguments(bundle);
        return newInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if(savedInstanceState != null) {
            mStops = savedInstanceState.getParcelableArrayList(STOPS);
            mFlight = savedInstanceState.getParcelable(FLIGHT);
        } else {
            Bundle bundle = this.getArguments();
            mFlight = bundle.getParcelable(FLIGHT);
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
                inflater.inflate(R.layout.fragment_directioninfo, container, false);

        getBaseActivity().getActivityComponent().fragmentComponent(new FragmentModule(this))
                .inject(this);

        setUnbinder(ButterKnife.bind(this, view));
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String s = getResources().getString(R.string.directioninfo_rout);
        mToolbar.setTitle(mFlight.getFlightNumber() + " " + s);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STOPS, (ArrayList<? extends Parcelable>) mStops);
        outState.putParcelable(FLIGHT, mFlight);
    }

    @Override
    protected void setUp(View view) {
        setupToolbar();
        setDirectionTitle();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        if(mStops == null) {
            mPresenter.getStopsOnDirection(mFlight.getCurrentDirection().getId());
        } else {
            mPresenter.getSavedStopsOnDirection();
        }

        ((MainActivity)getBaseActivity()).lockDrawer();
        ((MainActivity)getBaseActivity()).hideBottomNavigationView();
    }

    @Override
    public void showStopsOnDirection(List<StopVO> stops) {
        mStops = stops;
        mAdapter.addItems(mStops);
    }

    @Override
    public void showSavedStopsOnDirection() {
        mAdapter.addItems(mStops);
    }

    @Override
    public void openPreviousFragment() {
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void updateFlight(FlightVO flight) {
        mFlight = flight;
    }

    @Override
    public void setDirectionTitle() {
        mDirectionName.setText(mFlight.getCurrentDirection().getDirectionName());
    }

    @Override
    public void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.all_arrowbackblack_24dp);
        mToolbar.inflateMenu(R.menu.menu_directioninfo);
        mToolbar.getMenu().findItem(R.id.item_directioninfo_change)
                .setVisible(mFlight.getDirections().size() > 1);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_directioninfo_change:
                        mPresenter.clickedOnChangeDirectionButton(mFlight);
                        return true;
                }
                return false;
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clickedOnBackButton();
            }
        });
    }
}
