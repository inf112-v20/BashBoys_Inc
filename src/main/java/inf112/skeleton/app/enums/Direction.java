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
     * @param lr
     * @param dir
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
    public String toString(Direction dir) {
        switch(dir) {
        case NORTH:
            return "North";
        case WEST:
            return "West";
        case SOUTH:
            return "South";
        case EAST:
            return "East";
        default:
            return null;
        }
    }
}