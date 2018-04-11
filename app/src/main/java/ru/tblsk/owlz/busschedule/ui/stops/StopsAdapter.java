package ru.tblsk.owlz.busschedule.ui.stops;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.AppConstants;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

public class StopsAdapter extends RecyclerView.Adapter<BaseViewHolder>
        implements SectionTitleProvider{

    private List<StopVO> mStops;
    private  int mTypeAdapter;
    private RxEventBus mEventBus;

    @Inject
    public StopsAdapter(RxEventBus eventBus, int typeAdapter) {
        mTypeAdapter = typeAdapter;
        mEventBus = eventBus;
        mStops = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stop, parent, false);

        final StopViewHolder stopViewHolder = new StopViewHolder(itemView);

        stopViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = stopViewHolder.getAdapterPosition();
                StopVO stop = mStops.get(position);

                if(mTypeAdapter == AppConstants.ALL_STOPS_ADAPTER) {
                    SelectedStop.InAllStops selected = new SelectedStop.InAllStops(stop);
                    mEventBus.post(selected);
                } else {
                    SelectedStop.InHistoryStops selected = new SelectedStop.InHistoryStops(stop);
                    mEventBus.post(selected);
                }
            }
        });

        return stopViewHolder;
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
        @BindView(R.id.textview_stop_stopname)
        TextView mStopName;

        @BindView(R.id.imageview_stop_bench)
        ImageView mChangeDirection;

        public StopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if(mTypeAdapter == AppConstants.ALL_STOPS_ADAPTER) {
                mChangeDirection.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            mStopName.setText(mStops.get(position).getStopName());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
