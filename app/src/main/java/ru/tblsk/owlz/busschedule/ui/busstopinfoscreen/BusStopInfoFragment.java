package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.component.BusStopInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.OnBackPressedListener;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.BusScheduleContainerFragment;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections.FavoritesDirectionsDialog;
import ru.tblsk.owlz.busschedule.utils.CommonUtils;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;

public class BusStopInfoFragment extends BaseFragment
        implements BusStopInfoContract.View, SetupToolbar, OnBackPressedListener{

    public static final String TAG = "BusStopInfoFragment";
    public static final String STOP = "stop";
    public static final String IS_FAVORITE_STOP = "isFavoriteStop";
    public static final String FRAGMENT_ID = "fragmentId";

    @Inject
    BusStopInfoContract.Presenter mPresenter;

    @Inject
    BusStopInfoAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.toolbar_stopinfo)
    Toolbar mToolbar;

    @BindView(R.id.recyclerview_stopinfo)
    RecyclerView mRecyclerView;

    @BindDrawable(R.drawable.stopinfo_starborderblack_24dp)
    Drawable mWhiteStar;

    @BindDrawable(R.drawable.stopinfo_starblack_24dp)
    Drawable mTimati;

    private StopVO mStop;
    private boolean mIsFavorite;
    private long mFragmentId;
    private ComponentManager mComponentManager;

    public static BusStopInfoFragment newInstance(StopVO stop, boolean isFavoriteStop) {
        Bundle args = new Bundle();
        args.putParcelable(STOP, stop);
        args.putBoolean(IS_FAVORITE_STOP, isFavoriteStop);
        BusStopInfoFragment fragment = new BusStopInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setComponent() {
        mComponentManager = App.getApp(getContext()).getComponentManager();
        BusStopInfoScreenComponent component = mComponentManager
                .getBusStopInfoScreenComponent(mFragmentId);
        if(component == null) {
            component = mComponentManager.getNewBusStopInfoScreenComponent();
            mComponentManager.putBusStopInfoScreenComponent(mFragmentId, component);
        }
        component.add(new FragmentModule(getBaseActivity(), this))
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentId = savedInstanceState != null ? savedInstanceState.getLong(FRAGMENT_ID) :
                CommonUtils.NEXT_ID.getAndIncrement();
        mStop = getArguments().getParcelable(STOP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stopinfo, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
    protected void setUp() {
        setupToolbar();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        ((MainActivity)getBaseActivity()).lockDrawer();
        ((MainActivity)getBaseActivity()).hideBottomNavigationView();

        boolean isFavoriteStop = getArguments().getBoolean(IS_FAVORITE_STOP);
        mPresenter.attachView(this);
        mPresenter.getDirectionsByStop(mStop.getId(), isFavoriteStop);
    }

    @Override
    public void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.all_arrowbackblack_24dp);
        mToolbar.setTitle(mStop.getStopName());
        mToolbar.inflateMenu(R.menu.menu_stopinfo);
        mPresenter.isFavoriteStop(mStop.getId());

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clickedOnBackButton();
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_stopinfo_star:
                        mPresenter.clickedOnButtonAddFavorites();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showDirectionsByStop(List<DirectionVO> directions) {
        mAdapter.addItems(directions);
    }

    @Override
    public void openPreviousFragment() {
        mComponentManager.removeBusStopInfoScreenComponent(mFragmentId);
        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void openFavoritesDirectionsDialog(List<DirectionVO> directions) {
        if(mIsFavorite) {
            mPresenter.deleteFavoriteStop(mStop.getId());
            setFavoriteIcon(false);
        } else {
            FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
            FavoritesDirectionsDialog dialog =
                    FavoritesDirectionsDialog.newInstance(directions, mStop.getId(), mFragmentId);
            dialog.setTargetFragment(this, 0);
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
            dialog.show(fragmentManager, "FavoritesDirectionsDialog");
        }
    }

    @Override
    public void openScheduleContainerFragment(FlightVO flight) {
        FragmentManager fragmentManager = getBaseActivity()
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,
                BusScheduleContainerFragment.newInstance(mStop, flight));
        transaction.addToBackStack(BusStopInfoFragment.TAG);
        transaction.commit();
    }

    @Override
    public void setFavoriteIcon(boolean isFavorite) {
        if(isFavorite) {
            mToolbar.getMenu().findItem(R.id.item_stopinfo_star).setIcon(mTimati);
            mIsFavorite = true;
        } else {
            mToolbar.getMenu().findItem(R.id.item_stopinfo_star).setIcon(mWhiteStar);
            mIsFavorite = false;
        }
    }

    @Override
    public void showSnackBarDeleted() {
        getBaseActivity().showSnackBar(getString(R.string.stopinfo_deletefavorite));
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
