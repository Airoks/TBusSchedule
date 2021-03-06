package ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes;


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
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.ui.base.BaseViewHolder;
import ru.tblsk.owlz.busschedule.ui.base.CopyItems;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;

public class BusRoutesAdapter extends RecyclerView.Adapter<BaseViewHolder>
        implements CopyItems<FlightVO>{

    private static final int DIRECT_ID = DirectionType.DIRECT.id;
    private static final int REVERSE_ID = DirectionType.REVERSE.id;

    private List<FlightVO> mFlights;
    private BusRoutesContract.Presenter mPresenter;

    @Inject
    public BusRoutesAdapter(BusRoutesContract.Presenter presenter) {
        mFlights = new ArrayList<>();
        mPresenter = presenter;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);

        final BaseViewHolder viewHolder = new RoutesViewHolder(itemView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    mPresenter.clickedOnAdapterItem(position);
                }
            }
        });

        viewHolder.itemView.findViewById(R.id.imagebutton_route_change)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int position = viewHolder.getAdapterPosition();

                        if(mFlights.get(position).getCurrentDirectionType() == DIRECT_ID) {
                            FlightVO flightVO = mFlights.get(position);
                            flightVO.setCurrentDirectionType(REVERSE_ID);
                            mFlights.set(position, flightVO);
                        } else {
                            FlightVO flightVO = mFlights.get(position);
                            flightVO.setCurrentDirectionType(DIRECT_ID);
                            mFlights.set(position, flightVO);
                        }

                        notifyItemChanged(position);

                        int directionTypeName = mFlights.get(position).getCurrentDirectionType();
                        mPresenter.clickedOnDirectionChangeButton(position, directionTypeName);
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
        mFlights = getCopy(flights);
        notifyDataSetChanged();
    }

    @Override
    public List<FlightVO> getCopy(List<FlightVO> items) {
        List<FlightVO> flights = new ArrayList<>();
        for(FlightVO flight : items) {
            FlightVO copyFlight = new FlightVO();
            copyFlight.setId(flight.getId());
            copyFlight.setCurrentDirectionType(flight.getCurrentDirectionType());
            copyFlight.setFlightNumber(flight.getFlightNumber());
            copyFlight.setFlightType(flight.getFlightType());
            copyFlight.setPosition(flight.getPosition());
            copyFlight.setDirections(flight.getDirections());
            flights.add(copyFlight);
        }
        return flights;
    }

    class RoutesViewHolder extends BaseViewHolder {

        @BindView(R.id.textview_route_directionname)
        TextView mDirectionName;

        @BindView(R.id.textview_route_flightnumber)
        TextView mFlightNumber;

        @BindView(R.id.imagebutton_route_change)
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
                findByDirectionType(position, mFlights.get(position).getCurrentDirectionType());
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
