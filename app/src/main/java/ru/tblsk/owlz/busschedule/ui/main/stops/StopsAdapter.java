package ru.tblsk.owlz.busschedule.ui.main.stops;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;

public class StopsAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<Stop> mStops;

    public StopsAdapter(List<Stop> stops) {
        this.mStops = stops;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stop, parent, false);
        return new StopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mStops.size();
    }

    public void addItems(List<Stop> stops) {
        this.mStops.addAll(stops);
        notifyDataSetChanged();
    }
    class StopViewHolder extends BaseViewHolder {
        @BindView(R.id.stopTextView)
        TextView stopNameTextView;

        public StopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            stopNameTextView.setText(mStops.get(position).getStopName());
        }
    }
}
