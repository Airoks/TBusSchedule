package ru.tblsk.owlz.busschedule.ui.stopinfo.favoritesdirections;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;

public class FavoritesDirectionsAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<DirectionVO> mDirections;
    private FavoritesDirectionsContract.Presenter mPresenter;

    @Inject
    public FavoritesDirectionsAdapter(FavoritesDirectionsContract.Presenter presenter) {
        mPresenter = presenter;
        mDirections = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favoritesdirections, parent, false);

        final FavoritesDirectionsViewHolder holder = new FavoritesDirectionsViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                boolean isFavorite = mDirections.get(position).isFavorite();

                DirectionVO direction = mDirections.get(position);
                direction.setFavorite(!isFavorite);

                mPresenter.clickedOnAdapterItem(position, !isFavorite);

                mDirections.set(position, direction);
                notifyItemChanged(position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mDirections.size();
    }

    public void addItems(List<DirectionVO> directions) {
        mDirections.clear();
        mDirections = getDirections(directions);
        notifyDataSetChanged();
    }

    private List<DirectionVO> getDirections(List<DirectionVO> directions) {
        List<DirectionVO> copy = new ArrayList<>();
        List<DirectionVO> original;
        original = directions;
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

    class FavoritesDirectionsViewHolder extends BaseViewHolder {

        @BindView(R.id.textview_favoritesdirections_flightnumber)
        TextView mFlightNumber;

        @BindView(R.id.textview_favoritesdirections_directionname)
        TextView mDirectionName;

        @BindView(R.id.checkbox_favoritesdirections)
        CheckBox mAddInFavorite;

        public FavoritesDirectionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            if(mDirections.get(position).isFavorite()) {
                mAddInFavorite.setChecked(true);
            } else {
                mAddInFavorite.setChecked(false);
            }
            mFlightNumber.setText(mDirections.get(position).getFlightNumber());
            mDirectionName.setText(mDirections.get(position).getDirectionName());
        }
    }
}
