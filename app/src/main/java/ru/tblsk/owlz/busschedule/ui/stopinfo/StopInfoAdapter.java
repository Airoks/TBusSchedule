package ru.tblsk.owlz.busschedule.ui.stopinfo;


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
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

public class StopInfoAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<DirectionVO> mDirections;
    private RxEventBus mEventBus;

    @Inject
    public StopInfoAdapter(RxEventBus eventBus) {
        this.mEventBus = eventBus;
        this.mDirections = new ArrayList<>();
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
                StopInfoEvent event = new StopInfoEvent();
                event.setDirectionId(mDirections.get(position).getId());
                event.setDirectionType(mDirections.get(position).getDirectionType());
                mEventBus.post(event);
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

    class StopInfoViewHolder extends BaseViewHolder {

        @BindView(R.id.textview_stopinfo_directionname)
        TextView mDirectionName;

        @BindView(R.id.textview_stopinfo_flightnumber)
        TextView mFlightNumber;

        public StopInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            mDirectionName.setText(mDirections.get(position).getDirectionName());
            mFlightNumber.setText(mDirections.get(position).getFlightNumber());
        }
    }
}
