package ru.tblsk.owlz.busschedule.utils.mappers;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;

public class DepartureTimeMapper implements Function<DepartureTime, DepartureTimeVO>{
    @Override
    public DepartureTimeVO apply(DepartureTime departureTimes) throws Exception {
        if(departureTimes.getHours() == null) {
            return new DepartureTimeVO();
        } else {
            Map<Integer, ArrayList<Integer>> time = new HashMap<>(departureTimes.getTime());
            List<Integer> hours = new ArrayList<>(departureTimes.getHours());

            Collections.sort(hours);

            for(int hour : hours) {
                Collections.sort(time.get(hour));
            }


            DepartureTimeVO result = new DepartureTimeVO();
            result.setHours(hours);
            result.setTime(time);
            return result;
        }

    }
}
