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
import ru.tblsk.owlz.busschedule.di.component.BusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.routes.route.RoutesFragment;
import ru.tblsk.owlz.busschedule.utils.CommonUtils;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;

public class RoutesContainerFragment extends BaseFragment
        implements SetupToolbar, RoutesContainerContract.View{

    public static final String TAG = "RoutesContainerFragment";
    public static final String FRAGMENT_ID = "fragmentId";
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

    private long mFragmentId;

    public static RoutesContainerFragment newInstance() {
        return new RoutesContainerFragment();
    }

    @Override
    protected void setComponent() {
        ComponentManager componentManager = App.getApp(getContext()).getComponentManager();
        BusRoutesScreenComponent component = componentManager
                .getBusRoutesScreenComponent(mFragmentId);
        if(component == null) {
            component = componentManager.getNewBusRoutesScreenComponent();
            componentManager.putBusRoutesScreenComponent(mFragmentId, component);
        }
        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentId = savedInstanceState != null ? savedInstanceState.getLong(FRAGMENT_ID) :
                CommonUtils.NEXT_ID.getAndIncrement();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routescontainer, container, false);
        setUnbinder(ButterKnife.bind(this, view));
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
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(FRAGMENT_ID, mFragmentId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void setUp() {
        setupToolbar();
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

        ((MainActivity)getBaseActivity()).showBottomNavigationView();

        mPresenter.attachView(this);
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
        mPagerAdapter.addFragments(RoutesFragment.newInstance(mFragmentId, URBAN),
                getResources().getString(R.string.urban_routes));
        mPagerAdapter.addFragments(RoutesFragment.newInstance(mFragmentId, SUBURBAN),
                getResources().getString(R.string.suburban_routes));
        viewPager.setAdapter(mPagerAdapter);
    }
}
