package com.aconex.util;

import com.aconex.model.Direction;

public class DirectionUtil {

    public static Direction getDirectionAfterLeft(Direction currentDirection) {
        Direction direction = null;

        // Left command
        switch (currentDirection) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
        }

        return direction;
    }

    public static Direction getDirectionAfterRight(Direction currentDirection) {
        Direction direction = null;
        // Right command
        switch (currentDirection) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.NORTH;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
        }

        return direction;
    }
}
