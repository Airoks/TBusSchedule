package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;

public class UrbanRoutesFragment extends BaseFragment
        implements UrbanRoutesMvpView{

    public static final String TAG = "UrbanRoutesFragment";

    @Inject
    UrbanRoutesMvpPresenter<UrbanRoutesMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayout;

    UrbanRoutesAdapter urbanRoutesAdapter;

    @BindView(R.id.urbanRouteRv)
    RecyclerView mRecyclerView;



    public static UrbanRoutesFragment newInstance() {
        Bundle args = new Bundle();
        UrbanRoutesFragment fragment = new UrbanRoutesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        urbanRoutesAdapter = new UrbanRoutesAdapter();
        urbanRoutesAdapter.setSwapButton(new UrbanRoutesAdapter.UrbanRoutesListener() {
            @Override
            public void swapButtonOnClick(View view, int position) {
                Log.d("Button swap", "CLICK!!!!!!!");
            }
        });

        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(urbanRoutesAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_urban_routes, container, false);
        getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);
        setUnbinder(ButterKnife.bind(this, view));
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void showUrbanRoutes(List<Flight> flights) {

    }

    @Override
    public void changedDirection(ChangeDirectionUrban directionUrban) {

    }
}
