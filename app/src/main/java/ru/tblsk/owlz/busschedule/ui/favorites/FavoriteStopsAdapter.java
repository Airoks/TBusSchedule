package ru.tblsk.owlz.busschedule.ui.favorites;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

public class FavoriteStopsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<StopVO> mStops;
    private RxEventBus mEventBus;

    @Inject
    public FavoriteStopsAdapter(RxEventBus eventBus) {
        mEventBus = eventBus;
        mStops = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stop, parent, false);

        final StopViewHolder holder = new StopViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                mEventBus.post(mStops.get(position));
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
        return mStops.size();
    }

    public void addItems(List<StopVO> stops) {
        mStops.clear();
        mStops.addAll(stops);
        notifyDataSetChanged();
    }

    class StopViewHolder extends BaseViewHolder {

        @BindView(R.id.textview_stop_stopname)
        TextView mStopName;

        @BindView(R.id.imageview_stop_bench)
        ImageView mStopIcon;

        public StopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            mStopIcon.setVisibility(View.INVISIBLE);
            mStopName.setText(mStops.get(position).getStopName());
        }
    }
}
