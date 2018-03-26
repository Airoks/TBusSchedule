package ru.tblsk.owlz.busschedule.ui.directioninfo;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;

public class DirectionInfoAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<Stop> stops;

    public DirectionInfoAdapter() {
        this.stops = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void addItems(List<Stop> stops) {
        this.stops.clear();
        this.stops.addAll(stops);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DirectionInfoViewHolder extends BaseViewHolder {
        @BindView(R.id.textview_directioninfo_stopname)
        TextView mStopName;

        @BindView(R.id.view_directioninfo_topline)
        View mTopLine;

        @BindView(R.id.view_directioninfo_bottomline)
        View mBottomLine;

        public DirectionInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            mStopName.setText(stops.get(position).getStopName());

            mTopLine.setVisibility(View.VISIBLE);
            mBottomLine.setVisibility(View.VISIBLE);
            if(position == 0) {
                mTopLine.setVisibility(View.GONE);
            }
            if(position == (stops.size() - 1)) {
                mBottomLine.setVisibility(View.GONE);
            }
        }
    }
}
