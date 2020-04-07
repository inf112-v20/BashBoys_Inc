package inf112.skeleton.app.enums;

/**
 * Direction enums for MapObjects
 */
public enum Direction {

    NORTH,
    SOUTH,
    EAST,
    WEST;

    /**
     * Turn object in direction dir in either left or right
     * @param lr Direction of turn
     * @param dir Current direction
     * @return New direction after turn
     */
    public static Direction turn(LeftRight lr, Direction dir) {
        switch (dir) {
        case NORTH:
            if(lr == LeftRight.LEFT)return WEST;
            else return EAST;
        case WEST:
            if(lr == LeftRight.LEFT)return SOUTH;
            else return NORTH;
        case SOUTH:
            if(lr == LeftRight.LEFT)return EAST;
            else return WEST;
        case EAST:
            if(lr == LeftRight.LEFT)return NORTH;
            else return SOUTH;
        default:
            return null;
            
        }
    }

    /**
     * Do a uTurn in given direction
     * @param dir current direction
     * @return new direction after turn
     */
    public static Direction uTurn(Direction dir) {
        switch (dir) {
        case NORTH:
            return SOUTH;
        case WEST:
            return EAST;
        case SOUTH:
            return NORTH;
        case EAST:
            return WEST;
        default:
            return null;
            
        }
    }
    public static LeftRight relation(Direction oldD, Direction newD) {
        if(oldD == NORTH && newD == EAST) {
            return LeftRight.RIGHT;
        } else if(oldD == NORTH && newD == WEST) {
            return LeftRight.LEFT;
        } else if(oldD == SOUTH && newD == EAST) {
            return LeftRight.LEFT;
        } else if(oldD == SOUTH && newD == WEST) {
            return LeftRight.RIGHT;
        } else if(oldD == EAST && newD == NORTH) {
            return LeftRight.LEFT;
        } else if(oldD == EAST && newD == SOUTH) {
            return LeftRight.RIGHT;
        } else if(oldD == WEST && newD == NORTH) {
            return LeftRight.RIGHT;
        } else if(oldD == WEST && newD == SOUTH) {
            return LeftRight.LEFT;
        } else {
            throw new IllegalArgumentException("Directions not next too each other");
        }
    }
}