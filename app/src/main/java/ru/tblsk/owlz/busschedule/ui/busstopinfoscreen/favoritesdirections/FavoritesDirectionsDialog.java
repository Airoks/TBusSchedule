package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.favoritesdirections;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.component.BusStopInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseActivity;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.ui.busstopinfoscreen.BusStopInfoFragment;
import ru.tblsk.owlz.busschedule.utils.ComponentManager;

public class FavoritesDirectionsDialog extends DialogFragment
        implements FavoritesDirectionsContract.View{

    public static final String DIRECTIONS = "directions";
    public static final String STOP_ID = "stopId";
    public static final String FRAGMENT_ID = "fragmentId";

    @Inject
    FavoritesDirectionsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    FavoritesDirectionsContract.Presenter mPresenter;

    @BindView(R.id.recyclerview_favoritesdirections)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private long mFragmentId;
    private ComponentManager mComponentManager;

    public FavoritesDirectionsDialog() {
    }

    public static FavoritesDirectionsDialog newInstance(List<DirectionVO> directions,
                                                        long stopId, long fragmentId) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(DIRECTIONS, (ArrayList<? extends Parcelable>) directions);
        args.putLong(STOP_ID, stopId);
        args.putLong(FRAGMENT_ID, fragmentId);
        FavoritesDirectionsDialog fragment = new FavoritesDirectionsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void setComponent() {
        mComponentManager = App.getApp(getContext()).getComponentManager();
        BusStopInfoScreenComponent component = mComponentManager.getBusStopInfoScreenComponent(mFragmentId);
        component.add(new FragmentModule((BaseActivity)getActivity(), this))
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentId = getArguments().getLong(FRAGMENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialogfragment_favoritesdirections,
                container, false);
        setComponent();
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        setCancelable(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mPresenter.unsubscribeFromEvents();
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    public void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        List<DirectionVO> directions = getArguments().getParcelableArrayList(DIRECTIONS);
        mPresenter.setData(directions, getArguments().getLong(STOP_ID));
        mPresenter.getFavoritesDirections();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showDirections(List<DirectionVO> directions) {
        mAdapter.addItems(directions);
    }

    @Override
    public void addedFavoriteDirections(boolean isAdded) {
        if(isAdded) {
            ((BusStopInfoFragment)this.getTargetFragment()).setFavoriteIcon(true);
            ((BaseActivity)getContext()).showSnackBar(getString(R.string.stopinfo_addfavorite));
            getDialog().dismiss();
        } else {
            getDialog().dismiss();
            ((BaseActivity)getContext()).showSnackBar(getString(R.string.stopinfo_notselected));
        }
    }

    @OnClick(R.id.button_favoritesdirections_add)
    public void clickAddButton() {
       mPresenter.clickedOnAddButton();
    }

}
