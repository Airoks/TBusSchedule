package ru.tblsk.owlz.busschedule.ui.mappers;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.BiFunction;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;

public class DirectionMapper
        implements BiFunction<List<Direction>, List<String>, List<DirectionVO>> {
    @Override
    public List<DirectionVO> apply(List<Direction> directions, List<String> flightNumber)
            throws Exception {
        List<DirectionVO> directionsVO = new ArrayList<>();
        for(int i = 0; i < directions.size(); i ++) {
            DirectionVO directionVO = new DirectionVO();
            directionVO.setId(directions.get(i).getId());
            directionVO.setDirectionName(directions.get(i).getDirectionName());
            directionVO.setDirectionType(directions.get(i).getDirectionType().id);
            directionVO.setFlightId(directions.get(i).getFlightId());
            directionVO.setFlightNumber(flightNumber.get(i));
            directionVO.setFavorite(true);
            directionsVO.add(directionVO);
        }
        return directionsVO;
    }
}
