package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;

public class UrbanRoutesAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private UrbanRoutesListener onClickListener;

    private List<String> test;

    public UrbanRoutesAdapter() {
        test = new ArrayList<>();
        test.add("one");
        test.add("two");
        test.add("three");
        test.add("four");
        test.add("five");
        test.add("six");
        test.add("seven");
        test.add("eight");

    }

    public interface UrbanRoutesListener {
        void swapButtonOnClick(View view, int position);
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new UrbanRoutesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return test.size();
    }

    public void setSwapButton(UrbanRoutesListener urbanRoutesListener) {
        this.onClickListener = urbanRoutesListener;
    }

    class UrbanRoutesViewHolder extends BaseViewHolder {

        @BindView(R.id.XYU)
        Button button;

        public UrbanRoutesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.swapButtonOnClick(view, getAdapterPosition());
                }
            });
        }

        @Override
        public void onBind(int position) {
            button.setText(test.get(position));
        }
    }
}
