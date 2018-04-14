package ru.tblsk.owlz.busschedule.ui.stops;


import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public class StopsEvent {
    public static class InAllStops {
        public InAllStops(StopVO stop) {
            this.stop = stop;
        }

        private StopVO stop;

        public StopVO getStop() {
            return stop;
        }
    }

    public static class InViewedStops {
        private StopVO stop;

        public InViewedStops(StopVO stop) {
            this.stop = stop;
        }

        public StopVO getStop() {
            return stop;
        }
    }
}
