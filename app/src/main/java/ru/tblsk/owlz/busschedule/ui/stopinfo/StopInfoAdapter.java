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
import ru.tblsk.owlz.busschedule.ui.viewobject.DirectionVO;

public class StopInfoAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<DirectionVO> mDirections;

    @Inject
    public StopInfoAdapter() {
        this.mDirections = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stopinfo, parent, false);
        return new StopInfoViewHolder(itemView);
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
