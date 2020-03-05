package inf112.skeleton.app.object.belts;

import inf112.skeleton.app.enums.Direction;

public class MergeBelt extends Belt {

    public MergeBelt(Direction dir) {
        super(dir);
    }
    
    public MergeBelt(Direction dir, int strength) {
        super(dir,strength);
    }

    @Override
    public String getName() {
        return strength+"merge"+dir;
    }
}
