package ru.tblsk.owlz.busschedule.ui.favorites;


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
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;

import ru.tblsk.owlz.busschedule.di.component.DaggerFavoriteBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.FavoriteBusStopsScreenComponent;

import ru.tblsk.owlz.busschedule.di.module.FavoriteBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoFragment;
import ru.tblsk.owlz.busschedule.utils.CommonUtils;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;


public class FavoriteStopsFragment extends BaseFragment
        implements FavoriteStopsContract.View, SetupToolbar {

    public static final String TAG = "FavoriteStopsFragment";
    public static final String FRAGMENT_ID = "fragmentId";

    @Inject
    FavoriteStopsContract.Presenter mPresenter;

    @Inject
    FavoriteStopsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.toolbar_favoritestops)
    Toolbar mToolbar;

    @BindView(R.id.recyclerview_favoritestops)
    RecyclerView mRecyclerView;

    @BindView(R.id.textview_favoritestops_empty)
    TextView mEmptyTextView;

    private boolean isFavoriteStop;
    private long mFragmentId;

    public static FavoriteStopsFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteStopsFragment fragment = new FavoriteStopsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentId = savedInstanceState != null ? savedInstanceState.getLong(FRAGMENT_ID) :
                CommonUtils.NEXT_ID.getAndIncrement();
        isFavoriteStop = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favoritestops, container, false);

        ComponentManager componentManager = App.getApp(getContext()).getComponentManager();
        FavoriteBusStopsScreenComponent component = componentManager
                .getFavoriteBusStopsScreenComponent(mFragmentId);
        if(component == null) {
            component = componentManager.getNewFavoriteBusStopsScreenComponent();
            componentManager.putFavoriteBusStopsScreenComponent(mFragmentId, component);
        }

        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);


        setUnbinder(ButterKnife.bind(this, view));
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupToolbar();
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
    protected void setUp(View view) {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        ((MainActivity)getBaseActivity()).showBottomNavigationView();
        ((MainActivity)getBaseActivity()).unlockDrawer();

        mPresenter.getFavoriteStops();
    }

    @Override
    public void showFavoriteStops(List<StopVO> stops) {
        mAdapter.addItems(stops);
    }

    @Override
    public void openStopInfoFragment(StopVO stop) {
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, StopInfoFragment.newInstance(stop, isFavoriteStop));
        fragmentTransaction.addToBackStack(FavoriteStopsFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void openNavigationDrawer() {
        ((MainActivity)getBaseActivity()).openDrawer();
    }

    @Override
    public void showEmptyScreen() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyTextView.setVisibility(View.VISIBLE);
        mEmptyTextView.setText(getString(R.string.favoritestops_empty));
    }

    @Override
    public void setupToolbar() {

        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.all_menublack_24dp);
        mToolbar.setTitle(R.string.favorite);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clickedOnNavigation();
            }
        });

    }
}
