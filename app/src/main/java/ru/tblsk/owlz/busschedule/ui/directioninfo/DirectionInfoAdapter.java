package ru.tblsk.owlz.busschedule.ui.directioninfo;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

public class DirectionInfoAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private RxEventBus mEventBus;
    private List<Stop> mStops;

    @Inject
    public DirectionInfoAdapter(RxEventBus eventBus) {
        this.mEventBus = eventBus;
        this.mStops = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_directioninfo, parent, false);
        return new DirectionInfoAdapter.DirectionInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void addItems(List<Stop> stops) {
        this.mStops.clear();
        this.mStops.addAll(stops);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mStops.size();
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
            mStopName.setText(mStops.get(position).getStopName());

            mTopLine.setVisibility(View.VISIBLE);
            mBottomLine.setVisibility(View.VISIBLE);
            if(position == 0) {
                mTopLine.setVisibility(View.GONE);
            }
            if(position == (mStops.size() - 1)) {
                mBottomLine.setVisibility(View.GONE);
            }
        }
    }
}
