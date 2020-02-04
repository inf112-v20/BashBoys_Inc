package inf112.skeleton.app.enums;

public enum Direction {

    NORTH,
    SOUTH,
    EAST,
    WEST;

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
}