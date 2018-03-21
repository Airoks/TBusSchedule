package ru.tblsk.owlz.busschedule.ui.routes.urban;


import ru.tblsk.owlz.busschedule.data.db.model.Direction;

public class ChangeDirectionUrban {

    private Direction direction;

    public ChangeDirectionUrban(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public static class InFragment {

        private final int position;
        private final String directionType;

        public InFragment(int position, String directionType) {
            this.position = position;
            this.directionType = directionType;
        }

        public int getPosition() {
            return position;
        }

        public String getDirectionType() {
            return directionType;
        }
    }

    public static class InAdapter {

        private final int position;
        private final String directionType;

        public InAdapter(int position, String directionType) {
            this.position = position;
            this.directionType = directionType;
        }

        public int getPosition() {
            return position;
        }

        public String getDirectionType() {
            return directionType;
        }
    }
}
