package ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.screens.AllBusStops;
import ru.tblsk.owlz.busschedule.di.screens.allbusstops.component.AllBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.OnBackPressedListener;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoFragment;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.BusStopsAdapter;
import ru.tblsk.owlz.busschedule.utils.CommonUtils;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;

public class AllBusStopsFragment extends BaseFragment
        implements AllBusStopsContract.View, SetupToolbar, OnBackPressedListener{

    public static final String TAG = "AllBusStopsFragment";
    public static final String FRAGMENT_ID = "fragmentId";

    @Inject
    AllBusStopsContract.Presenter mPresenter;

    @Inject
    @AllBusStops
    BusStopsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.toolbar_allstop)
    Toolbar mToolbar;

    @BindView(R.id.recyclerview_allstop)
    RecyclerView mRecyclerView;

    @BindView(R.id.fastscroll_allstop)
    FastScroller mFastScroller;

    private boolean isFavoriteStop;
    private long mFragmentId;
    private ComponentManager mComponentManager;

    public static AllBusStopsFragment newInstance() {
        return new AllBusStopsFragment();
    }

    @Override
    protected void setComponent() {
        mComponentManager = App.getApp(getContext()).getComponentManager();
        AllBusStopsScreenComponent component = mComponentManager
                .getAllBusStopsScreenComponent(mFragmentId);
        if(component == null) {
            component = mComponentManager.getNewAllBusStopsScreenComponent();
            mComponentManager.putAllBusStopsScreenComponent(mFragmentId, component);
        }
        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentId = savedInstanceState != null ? savedInstanceState.getLong(FRAGMENT_ID) :
                CommonUtils.NEXT_ID.getAndIncrement();

        isFavoriteStop = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allstop, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mToolbar.setTitle(R.string.all_stops);
        setBackPressedListener();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(FRAGMENT_ID, mFragmentId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        mPresenter.unsubscribeFromEvents();
        super.onDestroy();
    }

    @Override
    public void showAllStops(List<StopVO> stops) {
        mAdapter.addItems(stops);
    }

    @Override
    public void openStopInfoFragment(StopVO stop) {
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, BusStopInfoFragment.newInstance(stop, isFavoriteStop));
        fragmentTransaction.addToBackStack(AllBusStopsFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void openPreviousFragment() {
        mComponentManager.removeAllBusStopsScreenComponent(mFragmentId);
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void showEmptyScreen() {

    }

    @Override
    protected void setUp() {
        setupToolbar();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mFastScroller.setRecyclerView(mRecyclerView);

        mPresenter.attachView(this);
        mPresenter.getAllStops();
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

    @Override
    public void setBackPressedListener() {
        View view = getView();
        if(view != null) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if(keyEvent.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        mPresenter.clickedOnBackButton();
                        return  true;
                    }
                    return false;
                }
            });
        }
    }
}
