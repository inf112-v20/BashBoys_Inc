package inf112.skeleton.app.object;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.interfaces.IDirectionalObject;

public class Pusher implements IDirectionalObject {

    private int x;
    private int y;
    private Direction dir;
    private boolean oneThreeFive;
    String name;

    public Pusher() {
        x = 0;
        y = 0;
        dir = Direction.NORTH;
        oneThreeFive = true;
    }

    public Pusher(int x, int y, Direction dir, boolean one) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        oneThreeFive = one;
    }
    
    public Pusher(Direction dir, boolean one) {
        this.dir = dir;
        this.oneThreeFive = one;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String getName() {
        return oneThreeFive ? "135pusher"+dir : "24pusher"+dir;
    }

    @Override
    public Direction getDir() {
        return dir;
    }
    
    public boolean getPhase() {
        return oneThreeFive;
    }

}
