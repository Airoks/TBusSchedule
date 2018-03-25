package ru.tblsk.owlz.busschedule.ui.directioninfo;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;

public class DirectionInfoAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DirectionInfoViewHolder extends BaseViewHolder {

        public DirectionInfoViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {

        }
    }
}
