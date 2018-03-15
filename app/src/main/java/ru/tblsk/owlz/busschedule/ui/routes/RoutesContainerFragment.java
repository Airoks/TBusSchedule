package ru.tblsk.owlz.busschedule.ui.routes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.SuburbanRouteFragment;
import ru.tblsk.owlz.busschedule.ui.routes.urban.UrbanRouteFragment;

public class RoutesContainerFragment extends BaseFragment
        implements SetupToolbar, RoutesContainerMvpView{

    @Inject
    RoutesContainerMvpPresenter<RoutesContainerMvpView> mPresenter;

    @Inject
    RoutesPagerAdapter mPagerAdapter;

    @BindView(R.id.routesContainerToolbar)
    Toolbar mToolbar;

    @BindView(R.id.routesContainerViewpager)
    ViewPager mViewPager;

    @BindView(R.id.routesContainerTab)
    TabLayout mTabLayout;

    public static RoutesContainerFragment newInstance() {
        Bundle args = new Bundle();
        RoutesContainerFragment fragment = new RoutesContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes_container, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mToolbar.setTitle(R.string.routs);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        setupToolbar();
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

        ((MainActivity)getBaseActivity()).showBottomNavigationView();
    }

    @Override
    public void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        mToolbar.setTitle(R.string.routs);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getBaseActivity()).openDrawer();
            }
        });


    }
    public void setupViewPager(ViewPager viewPager) {
        mPagerAdapter.addFragments(UrbanRouteFragment.newInstance(),
                getResources().getString(R.string.urban_routes));
        mPagerAdapter.addFragments(SuburbanRouteFragment.newInstance(),
                getResources().getString(R.string.suburban_routes));
        viewPager.setAdapter(mPagerAdapter);
    }
}
