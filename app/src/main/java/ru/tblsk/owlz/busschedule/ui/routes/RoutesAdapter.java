package ru.tblsk.owlz.busschedule.ui.routes;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.ChangeDirectionSuburban;
import ru.tblsk.owlz.busschedule.ui.routes.urban.ChangeDirectionUrban;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

public class RoutesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String DIRECT = "direct";
    private static final String REVERSE = "reverse";
    private static final int DIRECT_ID = 0;
    private static final int REVERSE_ID = 1;

    private List<Flight> mFlights;
    private List<Integer> mDirectionRouts;
    private RxEventBus mEventBus;
    private String mFlightType;

    @Inject
    public RoutesAdapter(RxEventBus eventBus, String flightType) {
        this.mEventBus = eventBus;
        this.mFlightType = flightType;
        mFlights = new ArrayList<>();
        mDirectionRouts = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new UrbanRoutesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stance = holder.getAdapterPosition();
                int directionId = mDirectionRouts.get(stance);
                List<Direction> directions = mFlights.get(stance).getDirections();

                for(Direction direction : directions) {
                    if(direction.getDirectionTypeId() == directionId) {
                        if(mFlightType.equals("urban")) {
                            mEventBus.post(new ChangeDirectionUrban(direction));
                        } else {
                            mEventBus.post(new ChangeDirectionSuburban(direction));
                        }
                    }
                }
            }
        });

        holder.itemView.findViewById(R.id.directionChangeButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int stance = holder.getAdapterPosition();
                        String directionName = mDirectionRouts.get(stance) == 0 ? REVERSE : DIRECT;

                        if(mDirectionRouts.get(stance) == DIRECT_ID) {
                            mDirectionRouts.set(stance, REVERSE_ID);
                        } else {
                            mDirectionRouts.set(stance, DIRECT_ID);
                        }

                        notifyItemChanged(stance);
                        ChangeDirectionUrban.InAdapter inAdapter =
                                new ChangeDirectionUrban.InAdapter(stance, directionName);
                        mEventBus.post(inAdapter);
                    }
                });

        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mFlights.size();
    }

    public void addItems(List<Flight> flights, List<String> directionRouts) {
        mDirectionRouts.clear();
        mFlights.clear();
        for(String s : directionRouts) {
            if(s.equals(DIRECT)) {
                mDirectionRouts.add(DIRECT_ID);
            } else {
                mDirectionRouts.add(REVERSE_ID);
            }
        }
        mFlights.addAll(flights);
        notifyDataSetChanged();
    }

    class UrbanRoutesViewHolder extends BaseViewHolder
            implements View.OnClickListener{

        @BindView(R.id.directionNameTextView)
        TextView mDirectionName;

        @BindView(R.id.flightNumberTextView)
        TextView mFlightNumber;

        @BindView(R.id.directionChangeButton)
        ImageButton mChangeButton;

        public UrbanRoutesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            mFlightNumber.setText(mFlights.get(position).getFlightNumber());

            int size = mFlights.get(position).getDirections().size();
            if(size >= 2) {
                mChangeButton.setVisibility(View.VISIBLE);
                findByDirectionType(position, mDirectionRouts.get(position));
            } else {
                mChangeButton.setVisibility(View.GONE);
                mDirectionName.setText(mFlights.get(position)
                        .getDirections().get(0).getDirectionName());
            }
        }

        @Override
        public void onClick(View view) {

        }

        private void findByDirectionType(int position, int directionId) {
            int caseOne = mFlights.get(position)
                    .getDirections().get(0).getDirectionTypeId().intValue();
            int caseTwo = mFlights.get(position)
                    .getDirections().get(1).getDirectionTypeId().intValue();

            if(directionId == caseOne) {
                mDirectionName.setText(mFlights.get(position)
                        .getDirections().get(0).getDirectionName());
            }
            if(directionId == caseTwo) {
                mDirectionName.setText(mFlights.get(position)
                        .getDirections().get(1).getDirectionName());
            }
        }
    }
}
