package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


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
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseActivity;
import ru.tblsk.owlz.busschedule.ui.stopinfo.StopInfoFragment;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;

public class FavoritesDirectionsDialog extends DialogFragment
        implements FavoritesDirectionsContract.View{

    public static final String DIRECTIONS = "directions";
    public static final String STOP_ID = "stopId";

    @Inject
    FavoritesDirectionsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    FavoritesDirectionsContract.Presenter mPresenter;

    @BindView(R.id.recyclerview_favoritesdirections)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private List<DirectionVO> mDirections;
    private long mStopId;

    public FavoritesDirectionsDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDirections = getDirections();
        mStopId = getArguments().getLong(STOP_ID);
    }

    public static FavoritesDirectionsDialog newInstance(List<DirectionVO> directions, long stopId) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(DIRECTIONS, (ArrayList<? extends Parcelable>) directions);
        args.putLong(STOP_ID, stopId);
        FavoritesDirectionsDialog fragment = new FavoritesDirectionsDialog();
        fragment.setArguments(args);
        return fragment;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = getView() != null ? getView() :
                inflater.inflate(R.layout.dialogfragment_favoritesdirections, container, false);

        ((BaseActivity)getContext()).getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);

        mUnbinder = ButterKnife.bind(this, view);

        mPresenter.attachView(this);
        mPresenter.subscribeOnEvents();

        setCancelable(true);

        return view;
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

        mPresenter.getFavoritesDirections();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showDirections() {
        mAdapter.addItems(mDirections);
    }

    @Override
    public void changeFavoriteDirections(int position, boolean isFavorite) {
        DirectionVO direction = mDirections.get(position);
        direction.setFavorite(isFavorite);
        mDirections.set(position, direction);
    }

    @OnClick(R.id.button_favoritesdirections_add)
    public void clickAddButton() {
        List<Long> directionsId = getDirectionsId();
        if(!directionsId.isEmpty()) {
            mPresenter.addFavoriteDirections(mStopId, directionsId);
            ((StopInfoFragment)this.getTargetFragment()).setFavoriteIcon(true);
            ((BaseActivity)getContext()).showSnackBar(getString(R.string.stopinfo_addfavorite));
            getDialog().dismiss();
        } else {
            getDialog().dismiss();
            ((BaseActivity)getContext()).showSnackBar(getString(R.string.stopinfo_notselected));
        }
    }

    private List<Long> getDirectionsId() {
        List<Long> directionsId = new ArrayList<>();
        for(DirectionVO direction : mDirections) {
            if(direction.isFavorite()) {
                directionsId.add(direction.getId());
            }
        }
        return directionsId;
    }

    private List<DirectionVO> getDirections() {
        List<DirectionVO> copy = new ArrayList<>();
        List<DirectionVO> original;
        original = getArguments().getParcelableArrayList(DIRECTIONS);
        for (DirectionVO direction : original) {
            DirectionVO copyDirection = new DirectionVO();

            copyDirection.setId(direction.getId());
            copyDirection.setFlightId(direction.getFlightId());
            copyDirection.setFavorite(direction.isFavorite());
            copyDirection.setFlightNumber(direction.getFlightNumber());
            copyDirection.setDirectionType(direction.getDirectionType());
            copyDirection.setDirectionName(direction.getDirectionName());

            copy.add(copyDirection);
        }
        return copy;
    }
}
