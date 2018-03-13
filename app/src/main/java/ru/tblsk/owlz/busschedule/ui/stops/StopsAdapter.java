package ru.tblsk.owlz.busschedule.ui.stops;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;

public class StopsAdapter extends RecyclerView.Adapter<BaseViewHolder>
        implements SectionTitleProvider{

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
        mStops.clear();
        mStops.addAll(stops);
        notifyDataSetChanged();
    }

    @Override
    public String getSectionTitle(int position) {
        String stopName = mStops.get(position).getStopName();
        boolean check = Character.isDigit(stopName.substring(0, 1).charAt(0));
        if(check) {
            return "#";
        }
        return stopName.substring(0, 1);
    }

    class StopViewHolder extends BaseViewHolder
            implements View.OnClickListener{
        @BindView(R.id.stopTextView)
        TextView stopNameTextView;

        public StopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            stopNameTextView.setText(mStops.get(position).getStopName());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
