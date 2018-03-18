package ru.tblsk.owlz.busschedule.ui.routes.urban;



public class ChangeDirectionUrban {

    public static class InFragment {

        private final int position;
        private final String direction;

        public InFragment(int position, String direction) {
            this.position = position;
            this.direction = direction;
        }

        public int getPosition() {
            return position;
        }

        public String getDirection() {
            return direction;
        }
    }

    public static class InAdapter {

        private final int position;
        private final String direction;

        public InAdapter(int position, String direction) {
            this.position = position;
            this.direction = direction;
        }

        public int getPosition() {
            return position;
        }

        public String getDirection() {
            return direction;
        }
    }
}
