package ru.tblsk.owlz.busschedule.ui.stops;


import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;

public class SelectedStop {
    public static class InAllStops {
        public InAllStops(StopVO stop) {
            this.stop = stop;
        }

        private StopVO stop;

        public StopVO getStop() {
            return stop;
        }
    }

    public static class InHistoryStops {
        private StopVO stop;

        public InHistoryStops(StopVO stop) {
            this.stop = stop;
        }

        public StopVO getStop() {
            return stop;
        }
    }
}
