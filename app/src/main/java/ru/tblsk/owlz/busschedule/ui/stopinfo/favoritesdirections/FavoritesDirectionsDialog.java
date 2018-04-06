package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseActivity;
import ru.tblsk.owlz.busschedule.ui.viewobject.DirectionVO;

public class FavoritesDirectionsDialog extends DialogFragment
        implements FavoritesDirectionsMvpView{

    public static final String DIRECTIONS = "directions";

    @Inject
    FavoritesDirectionsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @Inject
    FavoritesDirectionsMvpPresenter<FavoritesDirectionsMvpView> mPresenter;

    @BindView(R.id.recyclerview_favoritesdirections)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private List<DirectionVO> mDirections;

    public FavoritesDirectionsDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDirections = getArguments().getParcelableArrayList(DIRECTIONS);
    }

    public static FavoritesDirectionsDialog newInstance(List<DirectionVO> directions) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(DIRECTIONS, (ArrayList<? extends Parcelable>) directions);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialogfragment_favoritesdirections, container, false);

        ((BaseActivity)getContext()).getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);

        mUnbinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();

        setStyle(STYLE_NO_TITLE, 0);
    }

    public void setUp() {
        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
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
}
