package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.viewobject.DirectionVO;

public class FavoritesDirectionsDialog extends DialogFragment{

    public static final String DIRECTIONS = "directions";

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
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialogfragment_favoritesdirections, container);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }
}
