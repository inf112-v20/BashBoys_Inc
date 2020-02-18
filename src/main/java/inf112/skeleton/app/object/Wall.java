package inf112.skeleton.app.object;

import inf112.skeleton.app.IDirectionalObject;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;

public class Wall implements IDirectionalObject {

    private int x = 0;
    private int y = 0;
    private Direction dir = Direction.NORTH;
    
    public Wall(int x, int y, Direction dir ) {
        this.dir = dir;
        this.x = x;
        this.y = y;
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
        return dir.toString() + " Wall";
    }

    @Override
    public Direction getDir() {
        return dir;
    }
}
