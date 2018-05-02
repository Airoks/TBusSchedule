package ru.tblsk.owlz.busschedule.ui.directioninfoscreen;


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
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;

public class DirectionInfoAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private DirectionInfoContract.Presenter mPresenter;
    private List<StopVO> mStops;
    private List<NextFlight> mNextFlight;

    @Inject
    public DirectionInfoAdapter(DirectionInfoContract.Presenter presenter) {
        mPresenter = presenter;
        mStops = new ArrayList<>();
        mNextFlight = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_directioninfo, parent, false);

        final DirectionInfoViewHolder holder = new DirectionInfoViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                mPresenter.clickedOnAdapterItem(position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void addItems(List<StopVO> stops) {
        this.mStops.clear();
        this.mStops.addAll(stops);
        notifyDataSetChanged();
    }

    public void addTimeOfNextFlight(List<NextFlight> nextFlights) {
        this.mNextFlight.clear();
        this.mNextFlight.addAll(nextFlights);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mStops.size();
    }

    class DirectionInfoViewHolder extends BaseViewHolder {

        @BindView(R.id.textview_directioninfo_departuretime)
        TextView mDepartureTime;

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

            if(!mNextFlight.isEmpty()) {
                NextFlight next = mNextFlight.get(position);
                String time;
                if(next.isInitialized()) {
                    int timeBefore = next.getTimeBeforeDeparture();
                    int minute = timeBefore % 60;
                    int hour = (timeBefore - minute) / 60;

                    time = hour != 0 ? hour + "ч " + minute + "м" : minute + "м";
                    mDepartureTime.setText(time);
                }
            }

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
