package ru.tblsk.owlz.busschedule.ui.stops.historystops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsFragment;


public class StopsFragment extends BaseFragment implements StopsMvpView {

    @Inject
    StopsMvpPresenter<StopsMvpView> mPresenter;

    @Inject
    StopsAdapter mStopsAdapter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @BindView(R.id.stopToolbar)
    Toolbar mToolbar;

    @BindView(R.id.historyStopRv)
    RecyclerView mRecyclerView;

    public static StopsFragment newInstance() {
        return new StopsFragment();
    }
    @Override
    public void showSearchHistoryStops(List<Stop> stops) {
        mStopsAdapter.addItems(stops);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop, container, false);
        getActivityComponent().inject(this);
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
    protected void setUp(View view) {
        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mStopsAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);//резкая прокрутка
        mRecyclerView.setFocusable(false);//без начальной фокусациии на rv
        setupToolbar();
        mPresenter.getSearchHistoryStops();
    }

    @Override
    protected void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        mToolbar.setTitle(R.string.stops);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).openDrawer();
            }
        });
    }

    @OnClick(R.id.deleteButton)
    public void deleteSearchHistoryStops() {
        mPresenter.deleteSearchHistoryStops();
    }

    @OnClick(R.id.allStopsButton)
    public void allStops() {
        //транзакия для запуска фрагмента олстопс
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, AllStopsFragment.newInstance());
        fragmentTransaction.addToBackStack("to AllStopsFragment");

        fragmentTransaction.commit();
    }
}
