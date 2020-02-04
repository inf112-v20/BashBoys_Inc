package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public interface IDirectionalObject extends IMapObject {

    /**
     * Get direction
     */
    public Direction getDir();

    /**
     * turn direction by 1
     */
    public void turn(LeftRight lr);
    
    /**
     * 
     * @param lr LEFT or RIGHT
     * @param turnss times to turn
     */
    public void turn(LeftRight lr, int turnss);
}

