package ru.tblsk.owlz.busschedule.ui.busstopsscreens.viewedbusstopsscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.screens.ViewedBusStops;
import ru.tblsk.owlz.busschedule.di.screens.viewedbusstops.component.ViewedBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.BusStopsAdapter;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoFragment;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen.AllBusStopsFragment;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.CommonUtils;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;


public class ViewedBusStopsFragment extends BaseFragment
        implements ViewedBusStopsContract.View, SetupToolbar {

    public static final String TAG = "ViewedBusStopsFragment";
    public static final String FRAGMENT_ID = "fragmentId";

    @Inject
    ViewedBusStopsContract.Presenter mPresenter;

    @Inject
    @ViewedBusStops
    BusStopsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.toolbar_stop)
    Toolbar mToolbar;

    @BindView(R.id.recyclerview_stop)
    RecyclerView mRecyclerView;

    private boolean isFavoriteStop;
    private long mFragmentId;

    public static ViewedBusStopsFragment newInstance() {
        return new ViewedBusStopsFragment();
    }

    @Override
    protected void setComponent() {
        ComponentManager componentManager = App.getApp(getContext()).getComponentManager();
        ViewedBusStopsScreenComponent component = componentManager
                .getViewedBusStopsScreenComponent(mFragmentId);
        if(component == null) {
            component = componentManager.getNewViewedBusStopsScreenComponent();
            componentManager.putViewedBusStopsScreenComponent(mFragmentId, component);
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
        View view = inflater.inflate(R.layout.fragment_stop, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mToolbar.setTitle(R.string.stops);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mPresenter.unsubscribe();
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(FRAGMENT_ID, mFragmentId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void setUp() {
        setupToolbar();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);//резкая прокрутка
        mRecyclerView.setFocusable(false);//без начальной фокусациии на rv

        ((MainActivity)getBaseActivity()).unlockDrawer();
        ((MainActivity)getBaseActivity()).showBottomNavigationView();

        mPresenter.attachView(this);
        mPresenter.getSearchHistoryStops();
    }

    @Override
    public void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.all_menublack_24dp);
        mToolbar.setTitle(R.string.stops);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clickedOnNavigation();
            }
        });
    }

    @Override
    public void showSearchHistoryStops(List<StopVO> stops) {
        mAdapter.addItems(stops);
    }

    @Override
    public void showAllStopsFragment() {
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, AllBusStopsFragment.newInstance());
        fragmentTransaction.addToBackStack(ViewedBusStopsFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void openStopInfoFragment(StopVO stop) {
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, BusStopInfoFragment.newInstance(stop, isFavoriteStop));
        fragmentTransaction.addToBackStack(ViewedBusStopsFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void openNavigationDrawer() {
        ((MainActivity)getBaseActivity()).openDrawer();
    }

    @OnClick(R.id.imagebutton_stop_delete)
    public void deleteSearchHistoryStops() {
        mPresenter.deleteSearchHistoryStops();
    }

    @OnClick(R.id.button_stop_allstop)
    public void allStops() {
        mPresenter.clickedOnAllStopsButton();
    }
}
