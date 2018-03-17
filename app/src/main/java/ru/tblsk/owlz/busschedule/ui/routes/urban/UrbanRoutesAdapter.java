package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;

public class UrbanRoutesAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private UrbanRoutesListener onClickListener;

    private List<Flight> mFlights;

    public UrbanRoutesAdapter() {
        mFlights = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new UrbanRoutesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mFlights.size();
    }

    public void addItem(List<Flight> flights) {
        mFlights.clear();
        mFlights.addAll(flights);
        notifyDataSetChanged();
    }

    public void setSwapButton(UrbanRoutesListener urbanRoutesListener) {
        this.onClickListener = urbanRoutesListener;
    }

    public interface UrbanRoutesListener {
        void swapButtonOnClick(View view, int position);
    }

    class UrbanRoutesViewHolder extends BaseViewHolder {

        @BindView(R.id.directionNameTextView)
        TextView mDirectionName;

        @BindView(R.id.flightNumberTextView)
        TextView mFlightNumber;

        public UrbanRoutesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            mFlightNumber.setText(mFlights.get(position).getFlightNumber());
            mDirectionName.setText(mFlights.get(position).getDirections().get(0).getDirectionName());
        }
    }
}
