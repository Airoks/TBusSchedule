package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;

public class UrbanRoutesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String DIRECT = "direct";
    private static final String REVERSE = "reverse";

    private UrbanRoutesListener onClickListener;

    private List<Flight> mFlights;
    private List<Integer> mDirectionRouts;

    public UrbanRoutesAdapter() {
        mFlights = new ArrayList<>();
        mDirectionRouts = new ArrayList<>();
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

    public void addItems(List<Flight> flights, List<String> directionRouts) {
        mDirectionRouts.clear();
        mFlights.clear();
        for(String s : directionRouts) {
            if(s.equals(DIRECT)) {
                mDirectionRouts.add(0);
            } else {
                mDirectionRouts.add(1);
            }
        }
        mFlights.addAll(flights);
        notifyDataSetChanged();
    }

    public void setSwapButton(UrbanRoutesListener urbanRoutesListener) {
        this.onClickListener = urbanRoutesListener;
    }

    public interface UrbanRoutesListener {
        void swapButtonOnClick(View view, int position);
    }

    class UrbanRoutesViewHolder extends BaseViewHolder
            implements View.OnClickListener{

        @BindView(R.id.directionNameTextView)
        TextView mDirectionName;

        @BindView(R.id.flightNumberTextView)
        TextView mFlightNumber;

        @BindView(R.id.directionChangeButton)
        ImageButton mChangeButton;

        public UrbanRoutesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            mFlightNumber.setText(mFlights.get(position).getFlightNumber());

            int size = mFlights.get(position).getDirections().size();
            if(size >= 2) {
                mChangeButton.setVisibility(View.VISIBLE);
                int caseOne = mFlights.get(position)
                        .getDirections().get(0).getDirectionTypeId().intValue();
                int caseTwo = mFlights.get(position)
                        .getDirections().get(1).getDirectionTypeId().intValue();

                if(mDirectionRouts.get(position) == caseOne) {
                    mDirectionName.setText(mFlights.get(position)
                            .getDirections().get(0).getDirectionName());
                }
                if(mDirectionRouts.get(position) == caseTwo) {
                    mDirectionName.setText(mFlights.get(position)
                            .getDirections().get(1).getDirectionName());
                }
            } else {
                mChangeButton.setVisibility(View.GONE);
                mDirectionName.setText(mFlights.get(position)
                        .getDirections().get(0).getDirectionName());
            }
        }

        @Override
        public void onClick(View view) {

        }
    }
}
