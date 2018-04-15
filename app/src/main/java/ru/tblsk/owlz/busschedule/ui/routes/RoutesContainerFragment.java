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
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.component.AllBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.BusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.DaggerBusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.di.module.AllBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.module.BusRoutesScreenModule;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.routes.route.RoutesFragment;

public class RoutesContainerFragment extends BaseFragment
        implements SetupToolbar, RoutesContainerContract.View{

    public static final String TAG = "RoutesContainerFragment";
    public static final int URBAN = 0;
    public static final int SUBURBAN = 1;

    @Inject
    RoutesContainerContract.Presenter mPresenter;

    @Inject
    AllScreenPagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar_routescontainer)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_routescontainer)
    ViewPager mViewPager;

    @BindView(R.id.tablayout_routescontainer)
    TabLayout mTabLayout;

    public static RoutesContainerFragment newInstance() {
        return new RoutesContainerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routescontainer, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        /*getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);*/

        BusRoutesScreenComponent component = DaggerBusRoutesScreenComponent.builder()
                .busRoutesScreenModule(new BusRoutesScreenModule())
                .applicationComponent(App.getApp(getContext()).getApplicationComponent())
                .build();

        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);


        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mToolbar.setTitle(R.string.routs);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mToolbar.setNavigationIcon(R.drawable.all_menublack_24dp);
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
        mPagerAdapter.addFragments(RoutesFragment.newInstance(URBAN),
                getResources().getString(R.string.urban_routes));
        mPagerAdapter.addFragments(RoutesFragment.newInstance(SUBURBAN),
                getResources().getString(R.string.suburban_routes));
        viewPager.setAdapter(mPagerAdapter);
    }
}
