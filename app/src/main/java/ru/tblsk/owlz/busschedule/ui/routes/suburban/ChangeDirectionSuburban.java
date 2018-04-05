package ru.tblsk.owlz.busschedule.ui.routes.suburban;



public class ChangeDirectionSuburban {

    private int flightPosition;

    public ChangeDirectionSuburban(int flightPosition) {
        this.flightPosition = flightPosition;
    }

    public int getFlightPosition() {
        return flightPosition;
    }

    public static class InFragment {

        private final int position;
        private final int directionType;

        public InFragment(int position, int directionType) {
            this.position = position;
            this.directionType = directionType;
        }

        public int getPosition() {
            return position;
        }

        public int getDirectionType() {
            return directionType;
        }
    }

    public static class InAdapter {

        private final int position;
        private final int directionType;

        public InAdapter(int position, int directionType) {
            this.position = position;
            this.directionType = directionType;
        }

        public int getPosition() {
            return position;
        }

        public int getDirectionType() {
            return directionType;
        }
    }
}
