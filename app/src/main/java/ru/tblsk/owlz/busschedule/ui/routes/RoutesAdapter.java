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
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.ChangeDirectionSuburban;
import ru.tblsk.owlz.busschedule.ui.routes.urban.ChangeDirectionUrban;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

public class RoutesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String DIRECT = "direct";
    private static final String REVERSE = "reverse";
    private static final int DIRECT_ID = DirectionType.DIRECT.id;
    private static final int REVERSE_ID = DirectionType.REVERSE.id;

    private List<FlightVO> mFlights;
    private RxEventBus mEventBus;
    private int mFlightType;

    @Inject
    public RoutesAdapter(RxEventBus eventBus, int flightType) {
        this.mEventBus = eventBus;
        this.mFlightType = flightType;
        mFlights = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);

        final BaseViewHolder viewHolder = new RoutesViewHolder(itemView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stance = viewHolder.getAdapterPosition();
                int directionId = mFlights.get(stance).getCurrentDirection();
                List<Direction> directions = mFlights.get(stance).getDirections();

                for(Direction direction : directions) {
                    if(direction.getDirectionType().id == directionId) {
                        if(mFlightType == FlightType.URBAN.id) {
                            mEventBus.post(new ChangeDirectionUrban(direction, stance));
                        } else {
                            mEventBus.post(new ChangeDirectionSuburban(direction, stance));
                        }
                    }
                }
            }
        });

        viewHolder.itemView.findViewById(R.id.directionChangeButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int stance = viewHolder.getAdapterPosition();

                        if(mFlights.get(stance).getCurrentDirection() == DIRECT_ID) {
                            FlightVO flightVO = mFlights.get(stance);
                            flightVO.setCurrentDirection(REVERSE_ID);
                            mFlights.set(stance, flightVO);
                        } else {
                            FlightVO flightVO = mFlights.get(stance);
                            flightVO.setCurrentDirection(DIRECT_ID);
                            mFlights.set(stance, flightVO);
                        }

                        notifyItemChanged(stance);

                        int directionTypeName = mFlights.get(stance).getCurrentDirection();
                        if(mFlightType == FlightType.URBAN.id) {
                            mEventBus.post(new ChangeDirectionUrban
                                    .InAdapter(stance, directionTypeName));
                        } else {
                            mEventBus.post(new ChangeDirectionSuburban
                                    .InAdapter(stance, directionTypeName));
                        }
                    }
                });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mFlights.size();
    }

    public void addItems(List<FlightVO> flights) {
        mFlights.clear();
        mFlights.addAll(flights);
        notifyDataSetChanged();
    }

    class RoutesViewHolder extends BaseViewHolder {

        @BindView(R.id.directionNameTextView)
        TextView mDirectionName;

        @BindView(R.id.flightNumberTextView)
        TextView mFlightNumber;

        @BindView(R.id.directionChangeButton)
        ImageButton mChangeButton;

        public RoutesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            mFlightNumber.setText(mFlights.get(position).getFlightNumber());

            int size = mFlights.get(position).getDirections().size();
            if(size >= 2) {
                mChangeButton.setVisibility(View.VISIBLE);
                findByDirectionType(position, mFlights.get(position).getCurrentDirection());
            } else {
                mChangeButton.setVisibility(View.GONE);
                mDirectionName.setText(mFlights.get(position)
                        .getDirections().get(0).getDirectionName());
            }
        }

        private void findByDirectionType(int position, int directionId) {
            int caseOne = mFlights.get(position)
                    .getDirections().get(0).getDirectionType().id;
            int caseTwo = mFlights.get(position)
                    .getDirections().get(1).getDirectionType().id;

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
