package ru.tblsk.owlz.busschedule.ui.mappers;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;

public class FlightMapper implements Function<List<Flight>, List<FlightVO>>{
    @Override
    public List<FlightVO> apply(List<Flight> flights) throws Exception {
        List<FlightVO> flightVOList = new ArrayList<>();
        for(int i = 0; i < flights.size(); i ++) {
            FlightVO flightVO = new FlightVO();
            flightVO.setId(flights.get(i).getId());
            flightVO.setFlightNumber(flights.get(i).getFlightNumber());
            flightVO.setFlightType(flights.get(i).getFlightType().id);
            flightVO.setDirections(flights.get(i).getDirections());
            flightVO.setPosition(i);
            flightVO.setCurrentDirectionType(DirectionType.DIRECT.id);
            flightVOList.add(flightVO);
        }
        return flightVOList;
    }
}
