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
import ru.tblsk.owlz.busschedule.ui.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

public class FavoritesDirectionsAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<DirectionVO> mDirections;
    private RxEventBus mEventBus;

    @Inject
    public FavoritesDirectionsAdapter(RxEventBus eventBus) {
        this.mEventBus = eventBus;
        this.mDirections = new ArrayList<>();
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

                ChangeFavoriteDirection change = new ChangeFavoriteDirection();
                change.setPosition(position);
                if(isFavorite) {
                    change.setFavorite(false);
                    direction.setFavorite(false);
                } else {
                    change.setFavorite(true);
                    direction.setFavorite(true);
                }
                mEventBus.post(change);

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
        mDirections.addAll(directions);
        notifyDataSetChanged();
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
