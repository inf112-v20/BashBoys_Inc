package inf112.skeleton.app.object.belts;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public class CornerBelt extends Belt {

    private LeftRight lr;

    public CornerBelt(Direction dir, LeftRight lr) {
        super(dir);
        this.lr = lr;
    }
    
    public CornerBelt(Direction dir, int strength, LeftRight lr) {
        super(dir,strength);
        this.lr = lr;
    }

    //Used for drawing on board
    public LeftRight getTurn() {
        return lr;
    }
    
}
