package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen;


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
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.utils.NextFlight;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DirectionVO;

public class BusStopInfoAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<DirectionVO> mDirections;
    private List<NextFlight> mNextFlight;
    private BusStopInfoContract.Presenter mPresenter;

    @Inject
    public BusStopInfoAdapter(BusStopInfoContract.Presenter presenter) {
        mPresenter = presenter;
        mDirections = new ArrayList<>();
        mNextFlight = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stopinfo, parent, false);

        final StopInfoViewHolder holder = new StopInfoViewHolder(itemView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                long directionId = mDirections.get(position).getId();
                int directionType = mDirections.get(position).getDirectionType();
                mPresenter.clickedOnAdapterItem(directionId, directionType);
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
        mNextFlight.clear();
        mDirections.addAll(directions);
        notifyDataSetChanged();
    }

    public void addTimeOfNextFlight(List<NextFlight> nextFlights) {
        mNextFlight.clear();
        mNextFlight.addAll(nextFlights);
        notifyDataSetChanged();
    }

    class StopInfoViewHolder extends BaseViewHolder {

        @BindView(R.id.textview_stopinfo_directionname)
        TextView mDirectionName;

        @BindView(R.id.textview_stopinfo_flightnumber)
        TextView mFlightNumber;

        @BindView(R.id.textview_stopinfo_departuretime)
        TextView mDepartureTime;

        public StopInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            mDirectionName.setText(mDirections.get(position).getDirectionName());
            mFlightNumber.setText(mDirections.get(position).getFlightNumber());

            if(!mNextFlight.isEmpty()) {
                NextFlight next = mNextFlight.get(position);
                String time;
                if(next.isInitialized()) {
                    int timeBefore = next.getTimeBeforeDeparture();
                    int minute = timeBefore % 60;
                    int hour = (timeBefore - minute) / 60;

                    time = hour != 0 ? hour + "ч " : " ";
                    time = minute != 0 ? time + minute + "м" : time + " ";
                    mDepartureTime.setText(time);
                } else {
                    mDepartureTime.setText(" ");
                }
            } else {
                mDepartureTime.setText(" ");
            }
        }
    }
}
