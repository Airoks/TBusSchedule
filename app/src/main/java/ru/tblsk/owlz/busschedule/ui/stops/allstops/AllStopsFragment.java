package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;
import ru.tblsk.owlz.busschedule.ui.viewobject.StopVO;

public class AllStopsFragment extends BaseFragment
        implements AllStopsMvpView, SetupToolbar{

    public static final String TAG = "AllStopsFragment";

    @Inject
    AllStopsMvpPresenter<AllStopsMvpView> mPresenter;

    @Inject
    StopsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @BindView(R.id.allStopToolbar)
    Toolbar mToolbar;

    @BindView(R.id.allStopRv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fastScroll)
    FastScroller mFastScroller;

    private List<StopVO> mStops;

    public static AllStopsFragment newInstance() {
        return new AllStopsFragment();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mAdapter);
        mFastScroller.setRecyclerView(mRecyclerView);
        setupToolbar();
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
                //back to history stops fragment
                FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

    @Override
    public void showAllStops(List<StopVO> stops) {
        mStops = stops;
        mAdapter.addItems(stops);
    }

    @Override
    public void showSavedAllStops() {

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
    }
}
