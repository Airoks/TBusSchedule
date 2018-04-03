package ru.tblsk.owlz.busschedule.ui.stops.historystops;

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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.annotation.Type;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsFragment;
import ru.tblsk.owlz.busschedule.ui.viewobject.StopVO;


public class StopsFragment extends BaseFragment
        implements StopsMvpView, SetupToolbar {

    public static final String TAG = "StopsFragment";

    @Inject
    StopsMvpPresenter<StopsMvpView> mPresenter;

    @Inject
    @Type("stops")
    StopsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @BindView(R.id.toolbar_stop)
    Toolbar mToolbar;

    @BindView(R.id.recyclerview_stop)
    RecyclerView mRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static StopsFragment newInstance() {
        return new StopsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop, container, false);
        getBaseActivity().getActivityComponent().
                fragmentComponent(new FragmentModule(this)).inject(this);
        mPresenter.attachView(this);
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
        mRecyclerView.setNestedScrollingEnabled(false);//резкая прокрутка
        mRecyclerView.setFocusable(false);//без начальной фокусациии на rv
        setupToolbar();

        ((MainActivity)getBaseActivity()).unlockDrawer();
        ((MainActivity)getBaseActivity()).showBottomNavigationView();

        mPresenter.getSearchHistoryStops();
    }

    @Override
    public void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.all_menublack_24dp);
        mToolbar.setTitle(R.string.stops);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getBaseActivity()).openDrawer();
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
        fragmentTransaction.replace(R.id.container, AllStopsFragment.newInstance());
        fragmentTransaction.addToBackStack(AllStopsFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void openStopInfoFragment(StopVO stop) {

    }

    @OnClick(R.id.imagebutton_stop_delete)
    public void deleteSearchHistoryStops() {
        mPresenter.deleteSearchHistoryStops();
    }

    @OnClick(R.id.button_stop_allstop)
    public void allStops() {
        //транзакия для запуска фрагмента олстопс
        mPresenter.clickedOnAllStopsButton();
    }
}
