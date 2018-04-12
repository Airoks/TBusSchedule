package ru.tblsk.owlz.busschedule.ui.mappers;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DepartureTimeVO;

public class DepartureTimeMapper implements Function<List<DepartureTime>, List<DepartureTimeVO>>{
    @Override
    public List<DepartureTimeVO> apply(List<DepartureTime> departureTimes) throws Exception {
        List<DepartureTimeVO> result = new ArrayList<>();
        List<Integer> minuteList = new ArrayList<>();

        if(departureTimes.isEmpty()) {
            return Collections.emptyList();
        } else {
            minuteList.add(departureTimes.get(0).getMinute());
            for(int i = 1; i < departureTimes.size(); i ++) {
                int previousHours = departureTimes.get(i-1).getHours();
                int currentHours = departureTimes.get(i).getHours();
                if(previousHours == currentHours) {
                    minuteList.add(departureTimes.get(i).getMinute());
                } else {
                    Collections.sort(minuteList);
                    DepartureTimeVO time = new DepartureTimeVO();
                    time.setHours(previousHours);
                    time.setMinute(minuteList);
                    result.add(time);

                    minuteList.clear();
                    minuteList.add(departureTimes.get(i).getMinute());
                }
            }

            Collections.sort(minuteList);
            int size = departureTimes.size()-1;
            DepartureTimeVO time = new DepartureTimeVO();
            time.setHours(departureTimes.get(size).getHours());
            time.setMinute(minuteList);
            result.add(time);

            /*for(DepartureTimeVO timeVO : result) {
                Log.d("HOURS: ", Integer.toString(timeVO.getHours()));
                for(Integer minute : timeVO.getMinute()) {
                    Log.d("Minute: ", Integer.toString(minute));
                }
            }*/

            return result;
        }
    }
}
