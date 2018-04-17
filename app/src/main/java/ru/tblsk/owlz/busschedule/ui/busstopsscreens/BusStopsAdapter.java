package ru.tblsk.owlz.busschedule.ui.busstopsscreens;


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
import ru.tblsk.owlz.busschedule.ui.base.MvpPresenter;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.allbusstopsscreen.AllBusStopsContract;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.ui.busstopsscreens.viewedbusstopsscreen.ViewedBusStopsContract;
import ru.tblsk.owlz.busschedule.utils.AppConstants;

public class BusStopsAdapter extends RecyclerView.Adapter<BaseViewHolder>
        implements SectionTitleProvider{

    private List<StopVO> mStops;
    private  int mTypeAdapter;
    private AllBusStopsContract.Presenter mAllStopsPresenter;
    private ViewedBusStopsContract.Presenter mStopsPresenter;

    @Inject
    public BusStopsAdapter(int typeAdapter, MvpPresenter presenter) {
        if(typeAdapter == AppConstants.ALL_STOPS_ADAPTER) {
            mAllStopsPresenter = (AllBusStopsContract.Presenter) presenter;
        } else if(typeAdapter == AppConstants.VIEWED_STOPS_ADAPTER) {
            mStopsPresenter = (ViewedBusStopsContract.Presenter) presenter;
        }
        mTypeAdapter = typeAdapter;
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

                if(mTypeAdapter == AppConstants.ALL_STOPS_ADAPTER) {
                    mAllStopsPresenter.clickedOnAdapterItem(position);
                } else {
                    mStopsPresenter.clickedOnAdapterItem(position);
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
