package ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;

public class BusScheduleAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<Integer> mKeyMap;
    private Map<Integer, List<Integer>> mDepartureTime;

    public BusScheduleAdapter() {
        this.mKeyMap = new ArrayList<>();
        this.mDepartureTime = new HashMap<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        holder = new ScheduleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mDepartureTime.size();
    }

    public void addItems(DepartureTimeVO departureTimes) {
        mDepartureTime.clear();
        mKeyMap.clear();
        mKeyMap.addAll(departureTimes.getHours());
        mDepartureTime.putAll(departureTimes.getTime());
        notifyDataSetChanged();
    }

    class ScheduleViewHolder extends BaseViewHolder {

        @BindView(R.id.textview_schedule_hours)
        TextView mHours;

        @BindView(R.id.textview_schedule_minute)
        TextView mMinute;


        public ScheduleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            int key = mKeyMap.get(position);
            String hours = key + "";
            StringBuilder minute = new StringBuilder();
            for(Integer minuteList : mDepartureTime.get(key)) {
                minute.append(minuteList).append("\t\t");
            }
            mHours.setText(hours);
            mMinute.setText(minute);
        }
    }
}
