package ru.tblsk.owlz.busschedule.ui.base;


import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder{
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void onBind(int position) {

    }
}
