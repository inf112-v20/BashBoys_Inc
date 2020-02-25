package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public interface IDirectionalObject extends IMapObject {

    /**
     * Get direction
     */
    Direction getDir();

}

