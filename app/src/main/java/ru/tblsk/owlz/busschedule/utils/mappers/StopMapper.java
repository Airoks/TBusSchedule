package ru.tblsk.owlz.busschedule.utils.mappers;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.StopVO;

public class StopMapper implements Function<List<Stop>, List<StopVO>>{

    @Override
    public List<StopVO> apply(List<Stop> stops) throws Exception {
        List<StopVO> stopList = new ArrayList<>();
        for(Stop stop : stops) {
            StopVO stopVO = new StopVO();
            stopVO.setId(stop.getId());
            stopVO.setStopName(stop.getStopName());
            stopList.add(stopVO);
        }
        return stopList;
    }
}
