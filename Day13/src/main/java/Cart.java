class Cart implements Comparable<Cart>{
    int x;
    int y;
    Facing facing;
    private TurnDirection nextTurnDirection = TurnDirection.LEFT;

    // It should return a negative integer(usually -1), if the current triggering
    // object is less than the passed one, and positive integer (usually +1) if greater than, and 0 if equal.
    public int compareTo(Cart otherCart) {
        if (y < otherCart.y) {
            return -1;
        }

        if (y == otherCart.y && x < otherCart.x) {
            return -1;
        }

        return 1;
    }

    String getLocation() {
        return x + "," + y;
    }

    private void turnLeft() {
        facing = Facing.turnLeft(facing);
    }

    private void turnRight() {
        facing = Facing.turnRight(facing);
    }

    private void moveForward() {
            switch (facing) {
                case NORTH:
                    y -= 1;
                    break;
                case SOUTH:
                    y += 1;
                    break;
                case EAST:
                    x += 1;
                    break;
                case WEST:
                    x -= 1;
                    break;
        }
    }

    void act(String track) {
        moveForward();

        if (track.equals(Track.LEFT_CORNER.toString())) {
            if (facing == Facing.NORTH || facing == Facing.SOUTH) {
                turnLeft();
            } else {
                turnRight();
            }
        }

        if (track.equals(Track.RIGHT_CORNER.toString())) {
            if (facing == Facing.NORTH || facing == Facing.SOUTH) {
                turnRight();
            } else {
                turnLeft();
            }
        }

        if (track.equals(Track.INTERSECTION.toString())) {
            switch (nextTurnDirection) {
                case LEFT: {
                    turnLeft();
                    break;
                }
                case RIGHT: {
                    turnRight();
                    break;
                }
            }
            nextTurnDirection = TurnDirection.getNextDirection(nextTurnDirection);
        }



    }
}
