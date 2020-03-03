package inf112.skeleton.app.object;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public class CornerJoinBelt extends Belt {

    public CornerJoinBelt(Direction dir) {
        super(dir);
    }
    
    public CornerJoinBelt(Direction dir, int strength) {
        super(dir,strength);
    }

}
